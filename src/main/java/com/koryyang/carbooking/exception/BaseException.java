package com.koryyang.carbooking.exception;

import lombok.AllArgsConstructor;

/**
 * base exception
 * each exception we define should extend from it
 * @author yanglingyu
 * @date 2022/5/23
 */
@AllArgsConstructor
public abstract class BaseException extends RuntimeException {

    protected final String msg;

    @Override
    public String getMessage() {
        return msg;
    }

}
