package com.koryyang.carbooking.model.bo.order;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author yanglingyu
 * @date 2022/5/24
 */
@Data
public class CreateOrderBO {

    @NotBlank
    private String carModel;

    @NotNull
    private Long nums;

    @NotBlank
    private String userId;

}
