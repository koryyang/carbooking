package com.koryyang.carbooking.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * 插入自动注入
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.debug("createdAt自动注入");
        this.strictInsertFill(metaObject, "createdAt", LocalDateTime.class, LocalDateTime.now());
    }

    /**
     * 更新自动注入
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("updatedAt自动注入");
        this.strictUpdateFill(metaObject, "updatedAt", LocalDateTime.class, LocalDateTime.now());
    }
}
