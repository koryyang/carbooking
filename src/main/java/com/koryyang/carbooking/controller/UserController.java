package com.koryyang.carbooking.controller;

import com.koryyang.carbooking.model.request.user.UserLoginRequest;
import com.koryyang.carbooking.model.request.user.UserRegisterRequest;
import com.koryyang.carbooking.model.vo.Response;
import com.koryyang.carbooking.model.vo.user.UserLoginVO;
import com.koryyang.carbooking.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
@Validated
public class UserController {

    /**
     * user service
     */
    private final UserService userService;

    /**
     * user register
     * @param request request
     * @return response
     */
    @PostMapping("/register")
    public Response<?> register(@Valid @RequestBody UserRegisterRequest request) {
        userService.register(request);
        return Response.success();
    }

    /**
     * user login
     * @param request request
     * @return response
     */
    @PostMapping("/login")
    public Response<UserLoginVO> login(@Valid @RequestBody UserLoginRequest request) {
        UserLoginVO vo = userService.login(request);
        return Response.success(vo);
    }

}
