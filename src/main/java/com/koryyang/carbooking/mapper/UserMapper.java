package com.koryyang.carbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koryyang.carbooking.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
