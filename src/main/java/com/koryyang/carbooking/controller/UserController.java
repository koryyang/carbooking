package com.koryyang.carbooking.controller;

import com.koryyang.carbooking.aop.RolePermission;
import com.koryyang.carbooking.en.RoleEnum;
import com.koryyang.carbooking.model.request.tenant.UserLoginRequest;
import com.koryyang.carbooking.model.request.tenant.UserRegisterRequest;
import com.koryyang.carbooking.model.vo.Response;
import com.koryyang.carbooking.model.vo.tenant.UserLoginVO;
import com.koryyang.carbooking.service.UserService;
import com.koryyang.carbooking.utils.PasswordUtil;
import com.koryyang.carbooking.utils.ServletUtil;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
     * get public key to encrypt password
     * @return public key
     */
    @GetMapping("/public_key")
    public Response<String> getPublicKey() {
        String publicKey = PasswordUtil.getPublicKey();
        return Response.success(publicKey);
    }

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
    @RolePermission(allowRoles = {RoleEnum.OPERATOR, RoleEnum.TENANT})
    public Response<UserLoginVO> login(@Valid @RequestBody UserLoginRequest request) {
        UserLoginVO vo = userService.login(request);
        return Response.success(vo);
    }

    /**
     * user logout
     * @return response
     */
    @PostMapping("/logout")
    @RolePermission(allowRoles = {RoleEnum.OPERATOR, RoleEnum.TENANT})
    public Response<?> logout() {
        userService.logout(ServletUtil.getCurrentUser());
        return Response.success();
    }

}
