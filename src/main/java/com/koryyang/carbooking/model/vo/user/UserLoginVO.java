package com.koryyang.carbooking.model.vo.user;

import lombok.Data;

/**
 * user login vo
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class UserLoginVO {

    /**
     * user id
     * @see com.koryyang.carbooking.model.entity id
     */
    private String userId;

    /**
     * jwt
     */
    private String token;

}
