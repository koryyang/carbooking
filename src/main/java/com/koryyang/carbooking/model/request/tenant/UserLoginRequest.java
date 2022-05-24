package com.koryyang.carbooking.model.request.tenant;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class UserLoginRequest {

    @NotBlank(message = "account cannot be blank")
    private String account;

    @NotBlank(message = "password cannot be blank")
    private String encryptedPassword;

}
