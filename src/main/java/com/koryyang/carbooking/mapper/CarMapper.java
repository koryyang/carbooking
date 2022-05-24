package com.koryyang.carbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koryyang.carbooking.model.entity.CarEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * car mapper
 * @author yanglingyu
 * @date 2022/5/23
 */
@Mapper
public interface CarMapper extends BaseMapper<CarEntity> {

    /**
     * select car id set
     * @return car id set
     */
    Set<String> selectCarIdSet();

    /**
     * select car id set by model
     * @param model model
     * @return car id set
     */
    Set<String> selectCarIdSetByModel(@Param("model") String model);

}
