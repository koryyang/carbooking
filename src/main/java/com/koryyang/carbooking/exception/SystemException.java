package com.koryyang.carbooking.exception;

/**
 * system exception
 * it appears by system error
 * @author yanglingyu
 * @date 2022/5/23
 */
public class SystemException extends BaseException{

    private static final long serialVersionUID = 1L;

    public SystemException(String msg) {
        super(msg);
    }

}
