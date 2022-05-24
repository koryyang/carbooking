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
    public static final String LOCK_PREFIX = "lock:";

    /**
     * user token prefix
     */
    public static final String USER_TOKEN_PREFIX = "token:";

    /**
     * car id set
     */
    public static final String CAR_ID_SET = "car-id-set";

    /**
     * car id model map
     */
    public static final String CAR_ID_MODEL_MAP = "car-id-model-map";

    /**
     * car id set by model
     */
    public static final String CAR_ID_SET_BY_MODEL = "car-id-set:";

}
