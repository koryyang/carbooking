package com.koryyang.carbooking.model.bo.car;

import lombok.Data;

import java.time.LocalDate;

/**
 * @author yanglingyu
 * @date 2022/5/25
 */
@Data
public class CarBookingTableBO {

    private LocalDate bookStartDate;

    private LocalDate bookEndDate;

}
