package com.koryyang.carbooking.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public abstract class BaseEntity {

    /**
     * when does the data be created
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    /**
     * when does the data be updated
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedAt;

}
