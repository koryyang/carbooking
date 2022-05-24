package com.koryyang.carbooking.model.request.tenant;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * user register request param
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class UserRegisterRequest {

    /**
     * account
     */
    @NotBlank(message = "account not blank")
    private String account;

    /**
     * password
     */
    @NotBlank(message = "password not blank")
    private String password;

}
