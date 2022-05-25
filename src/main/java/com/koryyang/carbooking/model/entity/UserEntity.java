package com.koryyang.carbooking.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * database entity of user
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_user")
public class UserEntity extends BaseEntity {

    /**
     * primary key id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * account
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String account;

    /**
     * password after hash
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String password;

}
