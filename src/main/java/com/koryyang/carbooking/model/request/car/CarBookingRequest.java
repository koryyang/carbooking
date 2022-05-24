package com.koryyang.carbooking.model.request.car;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class CarBookingRequest {

    @NotBlank(message = "car model must be set")
    private String carModel;

    @NotNull(message = "car nums must be set")
    private Long nums;

}
