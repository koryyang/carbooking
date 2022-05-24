package com.koryyang.carbooking.model.vo.car;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class CarOrderVO {

    private String carModel;

    private Integer nums;

    private LocalDateTime bookTime;
}
