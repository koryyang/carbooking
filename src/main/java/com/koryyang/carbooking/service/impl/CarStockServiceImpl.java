package com.koryyang.carbooking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.koryyang.carbooking.constant.RedisConstant;
import com.koryyang.carbooking.exception.BusinessException;
import com.koryyang.carbooking.mapper.CarStockMapper;
import com.koryyang.carbooking.model.entity.CarStockEntity;
import com.koryyang.carbooking.model.request.car.CarAddRequest;
import com.koryyang.carbooking.model.request.car.CarQueryRequest;
import com.koryyang.carbooking.model.vo.car.CarQueryVO;
import com.koryyang.carbooking.service.CarStockService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * car stock service implement
 * @author yanglingyu
 * @date 2022/5/23
 */
@Service
@AllArgsConstructor
public class CarStockServiceImpl implements CarStockService {

    /**
     * redis
     */
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * car stock mapper
     */
    private final CarStockMapper carMapper;

    /**
     * redisson for lock
     */
    private final RedissonClient redissonClient;

    /**
     * query car
     * @param request request
     * @return result of query
     */
    @Override
    public List<CarQueryVO> queryCar(CarQueryRequest request) {
        Set<String> carModelSet = getCarModelSet();
        List<CarQueryVO> voList = new ArrayList<>();
        // trans to empty if blank
        String requestCarModel = StringUtils.defaultIfBlank(request.getCarModel(), Strings.EMPTY);
        for (String carModel : carModelSet) {
            if (StringUtils.contains(carModel, requestCarModel)) {
                long carStock = getCarStock(carModel);
                CarQueryVO vo = new CarQueryVO();
                vo.setCarModel(carModel);
                vo.setInStock(carStock);
                voList.add(vo);
            }
        }
        return voList;
    }

    /**
     * add car
     * @param request request
     */
    @SneakyThrows
    @Override
    public void addCar(CarAddRequest request) {
        CarStockEntity carStockEntity = carMapper.selectOne(new QueryWrapper<CarStockEntity>().lambda()
                .eq(CarStockEntity::getCarModel, request.getCarModel()));
        if (carStockEntity != null) {
            // existed model
            RLock lock = redissonClient.getLock(RedisConstant.LOCK_PREFIX + carStockEntity.getCarModel());
            if (lock.tryLock(RedisConstant.TRY_LOCK_WAIT_TIME, TimeUnit.SECONDS)) {
                try {
                    addCarStock(carStockEntity.getCarModel(), request.getNums());
                    return;
                } finally {
                    lock.unlock();
                }
            }
            throw new BusinessException("service busy, please try again later");
        } else {
            // new model
            addNewCarModel(request.getCarModel(), request.getNums());
        }
    }

    /**
     * judge if car model exist
     * @param carModel car model
     * @return car model exist or not
     */
    @Override
    public boolean isCarModelExist(String carModel) {
        Set<String> carModelSet = getCarModelSet();
        return carModelSet.contains(carModel);
    }

    /**
     * get all car models
     * @return set of car models
     */
    @Override
    public Set<String> getCarModelSet() {
        Set<String> carModelSet = redisTemplate.opsForSet().members(RedisConstant.KEY_CAR_MODEL);
        if (CollectionUtils.isEmpty(carModelSet)) {
            carModelSet = carMapper.selectAllCarModels();
            redisTemplate.opsForSet().add(RedisConstant.KEY_CAR_MODEL, carModelSet.toArray(new String[0]));
            redisTemplate.expire(RedisConstant.KEY_CAR_MODEL, RedisConstant.DATA_EXPIRE_TIME, TimeUnit.HOURS);
        }
        return carModelSet;
    }

    /**
     * get car stock from redis or database
     * @param carModel car model
     * @return car stock
     */
    @Override
    public long getCarStock(String carModel) {
        String stockString = redisTemplate.opsForValue().get(carModel);
        long stock;
        if (stockString == null) {
            Long count = carMapper.selectStock(carModel);
            redisTemplate.opsForValue().set(carModel, count.toString(), RedisConstant.DATA_EXPIRE_TIME, TimeUnit.HOURS);
            stock = count;
        } else {
            stock = Long.parseLong(stockString);
        }
        return stock;
    }

    /**
     * set car stock to redis and database
     * you should lock before call this method
     * @param carModel car model
     * @param reduceStock car stock
     */
    @SneakyThrows
    @Override
    public void reduceCarStock(String carModel, long reduceStock) {
        long carStock = getCarStock(carModel);
        assert carStock >= reduceStock;
        redisTemplate.opsForValue().set(carModel, Long.toString(carStock - reduceStock), RedisConstant.DATA_EXPIRE_TIME, TimeUnit.HOURS);
        // update database async
        CompletableFuture.runAsync(() -> carMapper.updateStock(carModel, carStock - reduceStock));
    }

    /**
     * add car stock to redis and database
     * you should lock before call this method
     * @param carModel car model
     * @param addStock add car stock
     */
    @Override
    public void addCarStock(String carModel, long addStock) {
        long carStock = getCarStock(carModel);
        redisTemplate.opsForValue().set(carModel, Long.toString(carStock + addStock));
        // update database async
        CompletableFuture.runAsync(() -> carMapper.updateStock(carModel, carStock + addStock));
    }

    /**
     * add new car model to redis and database
     * you should lock before call this method
     * @param carModel car model
     * @param stock car stock
     */
    private void addNewCarModel(String carModel, long stock) {
        redisTemplate.opsForValue().set(carModel, Long.toString(stock), RedisConstant.DATA_EXPIRE_TIME, TimeUnit.HOURS);
        // insert into database async
        CompletableFuture.runAsync(() -> {
            CarStockEntity entity = new CarStockEntity();
            entity.setCarModel(carModel);
            entity.setStock(stock);
            carMapper.insert(entity);
        });
    }

}
