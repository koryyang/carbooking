package com.koryyang.carbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koryyang.carbooking.model.entity.OrderEntity;
import com.koryyang.carbooking.model.vo.car.OrderQueryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * order mapper
 * @author yanglingyu
 * @date 2022/5/24
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

    /**
     * select order
     * @param userId user id
     * @return order list
     */
    List<OrderQueryVO> selectOrder(String userId);

}
