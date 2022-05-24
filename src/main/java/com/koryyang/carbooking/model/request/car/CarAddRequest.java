package com.koryyang.carbooking.model.request.car;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanglingyu
 * @date 2022/5/24
 */
@Data
public class CarAddRequest {

    /**
     * model of car
     */
    @NotBlank
    private String carModel;

    /**
     * nums of car
     */
    @NotNull
    private Long nums;

}
