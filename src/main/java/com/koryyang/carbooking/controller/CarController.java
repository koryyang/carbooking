package com.koryyang.carbooking.controller;

import com.koryyang.carbooking.model.request.car.CarBookingRequest;
import com.koryyang.carbooking.model.request.car.CarQueryRequest;
import com.koryyang.carbooking.model.vo.Response;
import com.koryyang.carbooking.model.vo.car.CarQueryVO;
import com.koryyang.carbooking.model.vo.car.OrderQueryVO;
import com.koryyang.carbooking.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@RestController
@RequestMapping("/api/v1/car")
@AllArgsConstructor
@Validated
public class CarController {

    /**
     * car service
     */
    private final CarService carService;

    /**
     * query car
     * @param request request
     * @return query result of car
     */
    @GetMapping("/query")
    public Response<Set<CarQueryVO>> queryCar(@Valid CarQueryRequest request) {
        Set<CarQueryVO> voSet = carService.queryCar(request);
        return Response.success(voSet);
    }

    /**
     * book car
     * @param request request
     * @return book result of car
     */
    @PostMapping("/book")
    public Response<?> bookCar(@RequestBody @Valid CarBookingRequest request) {
        carService.bookCar(request);
        return Response.success();
    }

    /**
     * query order
     * @return order list
     */
    @GetMapping("/order")
    public Response<List<OrderQueryVO>> queryOrder() {
        List<OrderQueryVO> voList = carService.queryOrder();
        return Response.success(voList);
    }

}
