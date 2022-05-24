package com.koryyang.carbooking.aop;

import com.koryyang.carbooking.en.RoleEnum;

import java.lang.annotation.*;

/**
 * api permission of role
 * @author yanglingyu
 * @date 2022/5/23
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RolePermission {

    /**
     * roles be allowed
     */
    RoleEnum[] allowRoles() default {};

}
