package com.koryyang.carbooking;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * application entry
 * @author yanglingyu
 */
@SpringBootApplication
@MapperScan(basePackages = "com.koryyang.carbooking.mapper")
@Slf4j
public class CarBookingApplication {

    public static void main(String[] args) {
        SpringApplication.run(CarBookingApplication.class, args);
        log.info("=====================================");
        log.info("========application started==========");
        log.info("=====================================");
    }

}
