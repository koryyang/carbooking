package com.koryyang.carbooking.utils;

import java.util.regex.Pattern;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
public class ValidatorUtil {

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    /**
     * 校验手机号
     *
     * @param phone 手机号
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPhone(String phone) {
        return Pattern.matches(REGEX_MOBILE, phone);
    }

}