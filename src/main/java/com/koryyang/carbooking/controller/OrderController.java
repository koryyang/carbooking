package com.koryyang.carbooking.controller;

import com.koryyang.carbooking.aop.RolePermission;
import com.koryyang.carbooking.en.RoleEnum;
import com.koryyang.carbooking.model.request.car.CarBookingRequest;
import com.koryyang.carbooking.model.request.order.CarReturnRequest;
import com.koryyang.carbooking.model.request.order.OrderQueryRequest;
import com.koryyang.carbooking.model.vo.Response;
import com.koryyang.carbooking.model.vo.car.CarBookingVO;
import com.koryyang.carbooking.model.vo.order.OrderQueryVO;
import com.koryyang.carbooking.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author yanglingyu
 * @date 2022/5/24
 */
@RestController
@RequestMapping("/api/v1/order")
@Validated
@AllArgsConstructor
public class OrderController {

    /**
     * order service
     */
    private final OrderService orderService;

    /**
     * book car
     * @param request request
     * @return response
     */
    @PostMapping("/book")
    @RolePermission(allowRoles = {RoleEnum.TENANT})
    public Response<CarBookingVO> bookCar(@RequestBody @Valid CarBookingRequest request) {
        CarBookingVO vo = orderService.bookCar(request);
        return Response.success(vo);
    }

    /**
     * query order
     * @param request request
     * @return response
     */
    @GetMapping("/query")
    @RolePermission(allowRoles = {RoleEnum.TENANT})
    public Response<List<OrderQueryVO>> queryOrder(@RequestParam OrderQueryRequest request) {
        List<OrderQueryVO> voList = orderService.queryOrder(request);
        return Response.success(voList);
    }

    /**
     * car return confirm
     * @param request request
     * @return response
     */
    @PutMapping("/return_confirm")
    @RolePermission(allowRoles = {RoleEnum.OPERATOR})
    public Response<?> carReturnConfirm(@RequestBody CarReturnRequest request) {
        orderService.carReturnConfirm(request);
        return Response.success();
    }

}
