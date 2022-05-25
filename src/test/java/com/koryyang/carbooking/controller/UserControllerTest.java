package com.koryyang.carbooking.controller;

import com.koryyang.carbooking.model.request.tenant.UserLoginRequest;
import com.koryyang.carbooking.model.request.tenant.UserRegisterRequest;
import com.koryyang.carbooking.model.vo.Response;
import com.koryyang.carbooking.model.vo.user.UserLoginVO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author yanglingyu
 * @date 2022/5/25
 */
@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void register() {
        UserRegisterRequest request = new UserRegisterRequest();
        request.setAccount("koryyang");
        request.setPassword("123456");
        Response<?> response = userController.register(request);
        assert response.getCode() == 200;
    }

    @Test
    void login() {
        UserLoginRequest request = new UserLoginRequest();
        request.setAccount("koryyang");
        request.setPassword("123456");
        Response<UserLoginVO> response = userController.login(request);
        assert response.getCode() == 200;
    }
}