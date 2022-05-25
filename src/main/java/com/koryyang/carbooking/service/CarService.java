package com.koryyang.carbooking.service;

import com.koryyang.carbooking.model.request.car.CarBookingRequest;
import com.koryyang.carbooking.model.request.car.CarQueryRequest;
import com.koryyang.carbooking.model.vo.car.CarQueryVO;
import com.koryyang.carbooking.model.vo.car.OrderQueryVO;

import java.util.List;
import java.util.Set;

/**
 * car service
 * @author yanglingyu
 * @date 2022/5/23
 */
public interface CarService {

    /**
     * query car
     * @param request request
     * @return result of query
     */
    Set<CarQueryVO> queryCar(CarQueryRequest request);

    /**
     * book car
     * @param request request
     */
    void bookCar(CarBookingRequest request);

    /**
     * query order
     * @return order list
     */
    List<OrderQueryVO> queryOrder();

}
