package com.koryyang.carbooking.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * database entity of car stock
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("car_stock")
public class CarStockEntity extends BaseEntity {

    /**
     * primary key id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * model of car
     */
    @TableField(insertStrategy = FieldStrategy.NOT_EMPTY)
    private String carModel;

    /**
     * stock of car
     */
    private Long stock;

}
