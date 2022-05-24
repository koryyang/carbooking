package com.koryyang.carbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koryyang.carbooking.model.entity.OrderEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanglingyu
 * @date 2022/5/24
 */
@Mapper
public interface OrderMapper extends BaseMapper<OrderEntity> {

}
