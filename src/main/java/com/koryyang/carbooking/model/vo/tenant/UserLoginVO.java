package com.koryyang.carbooking.model.vo.tenant;

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
     * role
     * @see com.koryyang.carbooking.en.RoleEnum
     */
    private String role;

    /**
     * jwt
     */
    private String token;

}
