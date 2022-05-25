package com.koryyang.carbooking.model.vo.car;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author yanglingyu
 * @date 2022/5/25
 */
@Data
public class OrderQueryVO {

    private String orderId;

    private String carId;

    private String model;

    private LocalDate bookStartDate;

    private LocalDate bookEndDate;

}
