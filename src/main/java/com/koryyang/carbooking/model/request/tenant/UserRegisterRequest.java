package com.koryyang.carbooking.model.request.tenant;

import com.koryyang.carbooking.aop.EnumStringValid;
import com.koryyang.carbooking.aop.PhoneValid;
import com.koryyang.carbooking.en.RoleEnum;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Request of user registration
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class UserRegisterRequest {

    @NotBlank(message = "account not blank")
    private String account;

    @NotBlank(message = "password not blank")
    private String encryptedPassword;

    @PhoneValid
    private String phone;

    @EnumStringValid(enumClass = RoleEnum.class, message = "invalid role")
    private String role;

}
