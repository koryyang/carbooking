package com.koryyang.carbooking.model.request.car;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * car booking request param
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class CarBookingRequest {

    @NotBlank(message = "car model must be set")
    private String carModel;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookStartDate;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookEndDate;

}
