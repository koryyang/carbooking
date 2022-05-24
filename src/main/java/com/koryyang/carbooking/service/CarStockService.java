package com.koryyang.carbooking.service;

import com.koryyang.carbooking.model.request.car.CarAddRequest;
import com.koryyang.carbooking.model.request.car.CarQueryRequest;
import com.koryyang.carbooking.model.vo.car.CarQueryVO;

import java.util.List;
import java.util.Set;

/**
 * car stock service
 * @author yanglingyu
 * @date 2022/5/23
 */
public interface CarStockService {

    /**
     * query car
     * @param request request
     * @return result of query
     */
    List<CarQueryVO> queryCar(CarQueryRequest request);

    /**
     * add car
     * @param request request
     */
    void addCar(CarAddRequest request);

    /**
     * judge if car model exist
     * @param carModel car model
     * @return car model exist or not
     */
    boolean isCarModelExist(String carModel);

    /**
     * get all car models
     * @return set of car models
     */
    Set<String> getCarModelSet();

    /**
     * get car stock from redis or database
     * @param carModel car model
     * @return car stock
     */
    long getCarStock(String carModel);

    /**
     * reduce car reduceStock to redis and database
     * you should lock before call this method
     * @param carModel car model
     * @param reduceStock reduce car stock
     */
    void reduceCarStock(String carModel, long reduceStock);

    /**
     * add car stock to redis and database
     * you should lock before call this method
     * @param carModel car model
     * @param addStock add car stock
     */
    void addCarStock(String carModel, long addStock);
}
