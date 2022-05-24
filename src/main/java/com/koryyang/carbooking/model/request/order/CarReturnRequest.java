package com.koryyang.carbooking.model.request.order;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yanglingyu
 * @date 2022/5/24
 */
@Data
public class CarReturnRequest {

    /**
     * order id
     */
    @NotBlank
    private String orderId;

}
