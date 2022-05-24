package com.koryyang.carbooking.model.vo.order;

import lombok.Data;

/**
 * @author yanglingyu
 * @date 2022/5/24
 */
@Data
public class OrderQueryVO {

    /**
     * order id
     */
    private String orderId;

    /**
     * car model
     */
    private String carModel;

    /**
     * car nums of the order
     */
    private Long nums;

    /**
     * has returned or not
     */
    private Boolean hasReturned;

}
