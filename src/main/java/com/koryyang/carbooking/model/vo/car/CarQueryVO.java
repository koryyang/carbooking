package com.koryyang.carbooking.model.vo.car;

import lombok.Data;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Data
public class CarQueryVO {

    /**
     * model of car
     */
    private String carModel;

    /**
     * stock of car
     */
    private Long inStock;

}
