package com.koryyang.carbooking.model.bo.user;

import lombok.Data;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class UserBO {

    private String userId;

    /**
     * @see com.koryyang.carbooking.en.RoleEnum
     */
    private String role;
}
