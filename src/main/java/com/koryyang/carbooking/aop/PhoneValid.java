package com.koryyang.carbooking.aop;

import com.koryyang.carbooking.aop.resolver.PhoneValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author yanglingyu
 * @date 2022/5/24
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneValidator.class)
public @interface PhoneValid {

    String message() default "invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


}
