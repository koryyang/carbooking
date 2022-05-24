package com.koryyang.carbooking.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.koryyang.carbooking.model.entity.CarStockEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Mapper
public interface CarStockMapper extends BaseMapper<CarStockEntity> {

    /**
     * select the stock of car model
     * @param carModel car model
     * @return stock
     */
    Long selectStock(@Param("carModel") String carModel);

    /**
     * update the stock of car model
     * @param carModel car model
     * @param stock    stock
     * @return effect rows
     */
    int updateStock(@Param("carModel") String carModel, @Param("stock") long stock);

    /**
     * select all car models
     * @return car models set
     */
    Set<String> selectAllCarModels();

}
