package com.koryyang.carbooking.exception;

/**
 * business exception
 * it appears by business error
 * @author yanglingyu
 * @date 2022/5/23
 */
public class BusinessException extends BaseException{

    private static final long serialVersionUID = 1L;

    public BusinessException(String msg) {
        super(msg);
    }

}
