package com.koryyang.carbooking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.koryyang.carbooking.constant.RedisConstant;
import com.koryyang.carbooking.exception.BusinessException;
import com.koryyang.carbooking.exception.SystemException;
import com.koryyang.carbooking.mapper.OrderMapper;
import com.koryyang.carbooking.model.bo.order.CreateOrderBO;
import com.koryyang.carbooking.model.entity.OrderEntity;
import com.koryyang.carbooking.model.request.car.CarBookingRequest;
import com.koryyang.carbooking.model.request.order.CarReturnRequest;
import com.koryyang.carbooking.model.request.order.OrderQueryRequest;
import com.koryyang.carbooking.model.vo.car.CarBookingVO;
import com.koryyang.carbooking.model.vo.order.OrderQueryVO;
import com.koryyang.carbooking.service.CarStockService;
import com.koryyang.carbooking.service.OrderService;
import com.koryyang.carbooking.utils.ServletUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * order service implement
 * @author yanglingyu
 * @date 2022/5/24
 */
@Service
@AllArgsConstructor
@Validated
@Slf4j
public class OrderServiceImpl implements OrderService {

    /**
     * order mapper
     */
    private final OrderMapper orderMapper;

    /**
     * car stock service
     */
    private final CarStockService carStockService;

    /**
     * redisson for lock
     */
    private final RedissonClient redissonClient;

    /**
     * book success and create a new order
     * @param bo bo param
     */
    private void createNewOrder(@Valid CreateOrderBO bo) {
        OrderEntity entity = new OrderEntity();
        entity.setCarModel(bo.getCarModel());
        entity.setNums(bo.getNums());
        entity.setUserId(ServletUtil.getCurrentUser().getUserId());
        entity.setHasReturned(false);
        orderMapper.insert(entity);
    }

    /**
     * book cars
     * @param request request
     * @return result of book cars
     */
    @SneakyThrows
    @Override
    public CarBookingVO bookCar(CarBookingRequest request) {
        if (!carStockService.isCarModelExist(request.getCarModel())) {
            throw new BusinessException("car model does not exist");
        }
        String carModel = request.getCarModel();
        long stock = carStockService.getCarStock(carModel);
        // not enough
        if (request.getNums() > stock) {
            throw new BusinessException("not enough");
        }
        // enough
        RLock lock = redissonClient.getLock(RedisConstant.LOCK_PREFIX + carModel);
        if (lock.tryLock(RedisConstant.TRY_LOCK_WAIT_TIME, TimeUnit.SECONDS)) {
            try {
                long stockNow = carStockService.getCarStock(carModel);
                // judge if enough again
                if (request.getNums() > stockNow) {
                    throw new BusinessException("not enough");
                }
                // enough
                // reduce stock
                carStockService.reduceCarStock(carModel, request.getNums());
                // create order async
                CompletableFuture.runAsync(() -> {
                    CreateOrderBO createOrderBO = new CreateOrderBO();
                    createOrderBO.setCarModel(carModel);
                    createOrderBO.setNums(request.getNums());
                    createOrderBO.setUserId(ServletUtil.getCurrentUser().getUserId());
                    createNewOrder(createOrderBO);
                });
                // return successful result
                CarBookingVO vo = new CarBookingVO();
                vo.setCarModel(carModel);
                vo.setNums(request.getNums());
                return vo;
            } finally {
                lock.unlock();
            }
        }
        throw new BusinessException("service busy, please try again later");
    }

    /**
     * query history order
     * @param request request
     * @return history order list
     */
    @Override
    public List<OrderQueryVO> queryOrder(OrderQueryRequest request) {
        // concat sql dynamically
        List<OrderEntity> entityList = orderMapper.selectList(new QueryWrapper<OrderEntity>().lambda()
                .eq(StringUtils.isNotBlank(request.getCarModel()), OrderEntity::getCarModel, request.getCarModel())
                .eq(Objects.nonNull(request.getHasReturned()), OrderEntity::getHasReturned, request.getHasReturned()));
        List<OrderQueryVO> voList = new ArrayList<>();
        for (OrderEntity entity : entityList) {
            OrderQueryVO vo = new OrderQueryVO();
            vo.setOrderId(entity.getId());
            vo.setCarModel(entity.getCarModel());
            vo.setNums(entity.getNums());
            vo.setHasReturned(entity.getHasReturned());
            voList.add(vo);
        }
        return voList;
    }

    /**
     * return car confirm
     * @param request request
     */
    @SneakyThrows
    @Override
    public void carReturnConfirm(CarReturnRequest request) {
        OrderEntity orderEntity = orderMapper.selectOne(new QueryWrapper<OrderEntity>().lambda()
                .eq(OrderEntity::getId, request.getOrderId()));
        if (orderEntity == null) {
            throw new SystemException("no such order");
        }
        if (Boolean.TRUE.equals(orderEntity.getHasReturned())) {
            // keep idempotent
            return;
        }
        RLock lock = redissonClient.getLock(RedisConstant.LOCK_PREFIX + orderEntity.getCarModel());
        if (lock.tryLock(RedisConstant.TRY_LOCK_WAIT_TIME, TimeUnit.SECONDS)) {
            try {
                carStockService.addCarStock(orderEntity.getCarModel(), orderEntity.getNums());
                CompletableFuture.runAsync(() -> {
                    orderEntity.setHasReturned(true);
                    orderMapper.updateById(orderEntity);
                });
                return;
            } finally {
                lock.unlock();
            }
        }
        throw new BusinessException("service busy, please try again later");
    }

}
