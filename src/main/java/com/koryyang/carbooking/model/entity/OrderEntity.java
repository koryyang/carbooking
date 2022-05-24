package com.koryyang.carbooking.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * database entity of order
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("t_order")
public class OrderEntity extends BaseEntity {

    /**
     * primary key id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * car id
     * @see com.koryyang.carbooking.model.entity.CarEntity id
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String carId;

    /**
     * user id
     * @see com.koryyang.carbooking.model.entity.UserEntity id
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String userId;

    @TableField(insertStrategy = FieldStrategy.NOT_NULL)
    private LocalDate bookStartDate;

    @TableField(insertStrategy = FieldStrategy.NOT_NULL)
    private LocalDate bookEndDate;

}
