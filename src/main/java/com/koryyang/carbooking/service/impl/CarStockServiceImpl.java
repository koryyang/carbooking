package com.koryyang.carbooking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.koryyang.carbooking.constant.RedisConstant;
import com.koryyang.carbooking.exception.BusinessException;
import com.koryyang.carbooking.mapper.CarMapper;
import com.koryyang.carbooking.mapper.OrderMapper;
import com.koryyang.carbooking.model.bo.car.CarBookingTableBO;
import com.koryyang.carbooking.model.entity.CarEntity;
import com.koryyang.carbooking.model.entity.OrderEntity;
import com.koryyang.carbooking.model.request.car.CarBookingRequest;
import com.koryyang.carbooking.model.request.car.CarQueryRequest;
import com.koryyang.carbooking.model.vo.car.CarQueryVO;
import com.koryyang.carbooking.model.vo.car.OrderQueryVO;
import com.koryyang.carbooking.service.CarService;
import com.koryyang.carbooking.utils.JacksonUtil;
import com.koryyang.carbooking.utils.ServletUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * car service implementation
 * @author yanglingyu
 * @date 2022/5/23
 */
@Service
@AllArgsConstructor
public class CarStockServiceImpl implements CarService {

    /**
     * redis
     */
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * car mapper
     */
    private final CarMapper carMapper;

    /**
     * order mapper
     */
    private final OrderMapper orderMapper;

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
    public Set<CarQueryVO> queryCar(CarQueryRequest request) {
        Assert.isTrue(!request.getBookStartDate().isBefore(LocalDate.now()), "invalid, start date before today");
        Assert.isTrue(!request.getBookEndDate().isBefore(request.getBookStartDate()), "invalid, end date before start date");
        Set<CarQueryVO> voSet = new HashSet<>();
        Set<String> carIdSet = getCarIdSet();
        // look each car whether it can be booked
        for (String carId : carIdSet) {
            if (canBook(carId, request.getBookStartDate(), request.getBookEndDate())) {
                CarQueryVO vo = new CarQueryVO();
                vo.setCarModel(getCarModelById(carId));
                voSet.add(vo);
            }
        }
        return voSet;
    }

    /**
     * book car
     * @param request request
     */
    @SneakyThrows
    @Override
    public void bookCar(CarBookingRequest request) {
        Assert.isTrue(!request.getBookStartDate().isBefore(LocalDate.now()), "invalid, start date before today");
        Assert.isTrue(!request.getBookEndDate().isBefore(request.getBookStartDate()), "invalid, end date before start date");
        Set<String> carIdSet = getCarIdSetByModel(request.getCarModel());
        for (String carId : carIdSet) {
            if (canBook(carId, request.getBookStartDate(), request.getBookEndDate())) {
                // lock this car before book
                RLock lock = redissonClient.getLock(RedisConstant.LOCK_PREFIX + carId);
                if (lock.tryLock(RedisConstant.TRY_LOCK_WAIT_TIME, TimeUnit.SECONDS)) {
                    try {
                        CarBookingTableBO bo = new CarBookingTableBO();
                        bo.setBookStartDate(request.getBookStartDate());
                        bo.setBookEndDate(request.getBookEndDate());
                        // add book date table of this car
                        redisTemplate.opsForSet().add(carId, JacksonUtil.toJson(bo));
                        String userId = ServletUtil.getCurrentUser().getUserId();
                        // insert order async to protect high efficiency
                        CompletableFuture.runAsync(() -> {
                            OrderEntity orderEntity = new OrderEntity();
                            orderEntity.setCarId(carId);
                            orderEntity.setUserId(userId);
                            orderEntity.setBookStartDate(request.getBookStartDate());
                            orderEntity.setBookEndDate(request.getBookEndDate());
                            orderMapper.insert(orderEntity);
                        });
                        return;
                    } finally {
                        lock.unlock();
                    }
                } else {
                    throw new BusinessException("service busy, please try again later");
                }
            }
        }
        throw new BusinessException("book failed，not enough of this model");
    }

    /**
     * query order
     * @return order list
     */
    @Override
    public List<OrderQueryVO> queryOrder() {
        return orderMapper.selectOrder(ServletUtil.getCurrentUser().getUserId());
    }

    /**
     * get every car id
     * @return car id set
     */
    private Set<String> getCarIdSet() {
        Set<String> resultSet = redisTemplate.opsForSet().members(RedisConstant.CAR_ID_SET);
        if (CollectionUtils.isEmpty(resultSet)) {
            // get data from database
            Set<String> carIdSet = carMapper.selectCarIdSet();
            if (!CollectionUtils.isEmpty(carIdSet)) {
                redisTemplate.opsForSet().add(RedisConstant.CAR_ID_SET, carIdSet.toArray(new String[0]));
                resultSet = carIdSet;
            }
        }
        return resultSet;
    }

    /**
     * get car model by id
     * @param carId car id
     * @return model
     */
    private String getCarModelById(String carId) {
        if (Boolean.TRUE.equals(redisTemplate.hasKey(RedisConstant.CAR_ID_MODEL_MAP))) {
            return redisTemplate.opsForHash().get(RedisConstant.CAR_ID_MODEL_MAP, carId).toString();
        }
        // get data from database
        List<CarEntity> entityList = carMapper.selectList(new QueryWrapper<>());
        String model = null;
        for (CarEntity entity : entityList) {
            redisTemplate.opsForHash().put(RedisConstant.CAR_ID_MODEL_MAP, entity.getId(), entity.getModel());
            if (entity.getId().equals(carId)) {
                model = entity.getModel();
            }
        }
        return model;
    }

    /**
     * get car id set by model
     * @param model model
     * @return car id set
     */
    private Set<String> getCarIdSetByModel(String model) {
        Set<String> resultSet = redisTemplate.opsForSet().members(RedisConstant.CAR_ID_SET_BY_MODEL + model);
        if (CollectionUtils.isEmpty(resultSet)) {
            // get data from database
            Set<String> carIdSet = carMapper.selectCarIdSetByModel(model);
            if (!CollectionUtils.isEmpty(carIdSet)) {
                redisTemplate.opsForSet().add(RedisConstant.CAR_ID_SET_BY_MODEL + model, carIdSet.toArray(new String[0]));
                resultSet = carIdSet;
            }
        }
        return resultSet;
    }

    /**
     * whether a car can be booked or not
     * @param carId car id
     * @param bookStartDate book start date
     * @param bookEndDate  book end date
     * @return whether a car can be booked
     */
    private boolean canBook(String carId, LocalDate bookStartDate, LocalDate bookEndDate) {
        // date table of a car that has booked
        Set<String> carBookingSet = redisTemplate.opsForSet().members(carId);
        Set<CarBookingTableBO> boSet = new HashSet<>();
        for (String json : carBookingSet) {
            CarBookingTableBO bo = JacksonUtil.toBean(json, CarBookingTableBO.class);
            boSet.add(bo);
        }
        // 核心就是去掉开始日期大于需订阅结束日期或者结束日期小于需订阅开始日期之后的数据，是否为空，空就表示可以租赁
        Set<CarBookingTableBO> collect = boSet.stream().filter(bo -> !(bo.getBookStartDate().isAfter(bookEndDate) || bo.getBookEndDate().isBefore(bookStartDate))).collect(Collectors.toSet());
        return collect.isEmpty();
    }

}
