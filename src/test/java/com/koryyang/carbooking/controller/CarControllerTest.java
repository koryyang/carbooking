package com.koryyang.carbooking.controller;

import com.koryyang.carbooking.model.bo.user.UserBO;
import com.koryyang.carbooking.model.request.car.CarBookingRequest;
import com.koryyang.carbooking.model.request.car.CarQueryRequest;
import com.koryyang.carbooking.model.vo.Response;
import com.koryyang.carbooking.model.vo.car.CarQueryVO;
import com.koryyang.carbooking.model.vo.car.OrderQueryVO;
import com.koryyang.carbooking.utils.ServletUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * @author yanglingyu
 * @date 2022/5/25
 */
@SpringBootTest
class CarControllerTest {

    @Autowired
    private CarController carController;

    @Test
    void queryCar() {
        CarQueryRequest request = new CarQueryRequest();
        request.setBookStartDate(LocalDate.now());
        request.setBookEndDate(LocalDate.now().plusDays(1));
        Response<Set<CarQueryVO>> response = carController.queryCar(request);
        assert response.getCode() == 200;
    }

    @Test
    void bookCar() {
        CarBookingRequest request = new CarBookingRequest();
        request.setCarModel("BMW");
        request.setBookStartDate(LocalDate.now());
        request.setBookEndDate(LocalDate.now().plusDays(1));
        Response<?> response = carController.bookCar(request);
        assert response.getCode() == 200;
    }

    @Test
    void queryOrder() {
        UserBO userBO = new UserBO();
        userBO.setUserId("6d7275baf0758197749c7a901fb55bb9");
        userBO.setAccount("yanglingyu");
        ServletUtil.setCurrentUser(userBO);
        Response<List<OrderQueryVO>> response = carController.queryOrder();
        assert response.getCode() == 200;
    }
}