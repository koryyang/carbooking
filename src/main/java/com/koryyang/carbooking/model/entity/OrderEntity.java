package com.koryyang.carbooking.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * database entity of order
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("order")
public class OrderEntity extends BaseEntity {

    /**
     * primary key id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * car model
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String carModel;

    /**
     * nums of car
     */
    private long nums;

    /**
     * user id
     * @see com.koryyang.carbooking.model.entity.UserEntity id
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String userId;

    /**
     * has returned or not
     */
    private Boolean hasReturned;

}
