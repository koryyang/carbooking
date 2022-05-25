package com.koryyang.carbooking.model.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Response<T> {

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 信息
     */
    private final String msg;

    /**
     * 内容
     */
    private final T content;

    //========================成功返回=============================

    public static <T> Response<T> success() {
        return new Response<T>(200, "success", null);
    }

    public static <T> Response<T> success(T content) {
        return new Response<>(200, "success", content);
    }

    //========================业务异常返回=============================

    public static <T> Response<T> businessError(String msg) {
        return new Response<>(400, msg, null);
    }

    //========================系统异常返回=============================

    public static <T> Response<T> systemError() {
        return new Response<T>(500, "unexpected error", null);
    }

}

