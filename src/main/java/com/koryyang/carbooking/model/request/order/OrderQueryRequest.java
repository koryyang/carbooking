package com.koryyang.carbooking.model.request.order;

import lombok.Data;

/**
 * order query request param
 * @author yanglingyu
 * @date 2022/5/24
 */
@Data
public class OrderQueryRequest {

    /**
     * car model (not necessary, condition will be ignored if blank)
     */
    private String carModel;

    /**
     * has returned (not necessary, condition will be ignored if null)
     */
    private Boolean hasReturned;

}
