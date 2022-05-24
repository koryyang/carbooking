package com.koryyang.carbooking.aop;

import com.koryyang.carbooking.aop.resolver.EnumStringValidator;
import com.koryyang.carbooking.en.ParentEnum;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EnumStringValidator.class})
@Documented
public @interface EnumStringValid {

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<?>[] target() default {};

    /**
     * 允许的枚举
     */
    Class<? extends ParentEnum> enumClass();

}
