package com.koryyang.carbooking.service;

import com.koryyang.carbooking.model.request.car.CarBookingRequest;
import com.koryyang.carbooking.model.request.order.CarReturnRequest;
import com.koryyang.carbooking.model.request.order.OrderQueryRequest;
import com.koryyang.carbooking.model.vo.car.CarBookingVO;
import com.koryyang.carbooking.model.vo.order.OrderQueryVO;

import java.util.List;

/**
 * order service
 * @author yanglingyu
 * @date 2022/5/24
 */
public interface OrderService {

    /**
     * book cars
     * @param request request
     * @return result of book cars
     */
    CarBookingVO bookCar(CarBookingRequest request);

    /**
     * query history order
     * @param request request
     * @return history order list
     */
    List<OrderQueryVO> queryOrder(OrderQueryRequest request);

    /**
     * return car confirm
     * @param request request
     */
    void carReturnConfirm(CarReturnRequest request);

}
