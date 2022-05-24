package com.koryyang.carbooking.controller;

import com.koryyang.carbooking.aop.RolePermission;
import com.koryyang.carbooking.en.RoleEnum;
import com.koryyang.carbooking.model.request.car.CarAddRequest;
import com.koryyang.carbooking.model.request.car.CarQueryRequest;
import com.koryyang.carbooking.model.vo.Response;
import com.koryyang.carbooking.model.vo.car.CarQueryVO;
import com.koryyang.carbooking.service.CarStockService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@RestController
@RequestMapping("api/v1/car")
@AllArgsConstructor
public class CarStockController {

    /**
     * car stock service
     */
    private final CarStockService carService;

    /**
     * query car
     * @param request request
     * @return query result of car
     */
    @GetMapping("/query")
    @RolePermission(allowRoles = {RoleEnum.TENANT, RoleEnum.OPERATOR})
    public Response<List<CarQueryVO>> queryCar(@RequestParam CarQueryRequest request) {
        List<CarQueryVO> voList = carService.queryCar(request);
        return Response.success(voList);
    }

    /**
     * add new car model
     * @param request request
     * @return result
     */
    @PostMapping("/add")
    @RolePermission(allowRoles = {RoleEnum.OPERATOR})
    public Response<?> addCar(@RequestBody CarAddRequest request) {
        carService.addCar(request);
        return Response.success();
    }

}
