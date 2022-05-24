package com.koryyang.carbooking.model.request.car;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * car query request param
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class CarQueryRequest {

    /**
     * start date of query
     */
    @NotNull(message = "book start date not null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookStartDate;

    /**
     * end date of query
     */
    @NotNull(message = "book end date not null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate bookEndDate;

}
