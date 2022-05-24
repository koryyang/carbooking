package com.koryyang.carbooking.aop.resolver;

import com.koryyang.carbooking.aop.PhoneValid;
import com.koryyang.carbooking.utils.ValidatorUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author yanglingyu
 * @date 2022/5/24
 */
public class PhoneValidator implements ConstraintValidator<PhoneValid, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isBlank(s)) {
            return true;
        }
        return ValidatorUtil.isPhone(s);
    }
}
