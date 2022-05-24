package com.koryyang.carbooking.en;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * role enum
 * @author yanglingyu
 * @date 2022/5/23
 */
@AllArgsConstructor
@Getter
public enum RoleEnum implements ParentEnum {

    /**
     * Tenant
     * who can book car
     */
    TENANT("tenant"),

    /**
     * Operator
     * who operate the car rental shop
     */
    OPERATOR("operator");

    private String type;

}
