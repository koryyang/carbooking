package com.koryyang.carbooking.constant;

/**
 * Redis Constant
 * @author yanglingyu
 * @date 2022/5/24
 */
public class RedisConstant {

    /**
     * data expire in 2 hours
     */
    public static final Integer DATA_EXPIRE_TIME = 2;

    /**
     * try lock and wait 5 seconds
     */
    public static final Integer TRY_LOCK_WAIT_TIME = 5;

    /**
     * lock key prefix
     */
    public static final String LOCK_PREFIX = "lock-";

    /**
     * car model key
     */
    public static final String KEY_CAR_MODEL = "car-model";

}
