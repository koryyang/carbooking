package com.koryyang.carbooking.aop.resolver;

import com.koryyang.carbooking.aop.EnumStringValid;
import com.koryyang.carbooking.en.ParentEnum;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
public class EnumStringValidator implements ConstraintValidator<EnumStringValid, String> {

    private Class<? extends ParentEnum> enumClass;

    @Override
    public void initialize(EnumStringValid enumStringValid) {
        enumClass = enumStringValid.enumClass();
    }

    @Override
    public boolean isValid(String type, ConstraintValidatorContext context) {
        if (StringUtils.isBlank(type)) {
            return false;
        }
        //不为空的时候要校验
        ParentEnum[] parentEnums = enumClass.getEnumConstants();
        for (ParentEnum parentEnum : parentEnums) {
            if (parentEnum.getType().equals(type)) {
                return true;
            }
        }
        return false;
    }
}
