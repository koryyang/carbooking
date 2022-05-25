package com.koryyang.carbooking.service;

import com.koryyang.carbooking.model.request.tenant.UserLoginRequest;
import com.koryyang.carbooking.model.request.tenant.UserRegisterRequest;
import com.koryyang.carbooking.model.vo.user.UserLoginVO;

/**
 * user service
 * @author yanglingyu
 * @date 2022/5/23
 */
public interface UserService {

    /**
     * register
     * @param request request
     */
    void register(UserRegisterRequest request);

    /**
     * login
     * @param request request
     * @return login result
     */
    UserLoginVO login (UserLoginRequest request);

}
