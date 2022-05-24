package com.koryyang.carbooking.model.request.car;

import lombok.Data;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class CarOrderRequest {

    /**
     * model of car
     */
    private String carModel;

    /**
     * start timestamp of your query
     */
    private Long startTimestamp;

    /**
     * end timestamp of your query
     */
    private Long endTimestamp;

}
