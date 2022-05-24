package com.koryyang.carbooking.service;

import com.koryyang.carbooking.model.bo.user.UserBO;
import com.koryyang.carbooking.model.request.tenant.UserLoginRequest;
import com.koryyang.carbooking.model.request.tenant.UserRegisterRequest;
import com.koryyang.carbooking.model.vo.tenant.UserLoginVO;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
public interface UserService {

    /**
     * register
     * @param request request
     */
    @Transactional(rollbackFor = Exception.class)
    void register(UserRegisterRequest request);

    /**
     * login
     * @param request request
     * @return login result
     */
    UserLoginVO login (UserLoginRequest request);

    /**
     * logout
     * @param userBO userBO
     */
    void logout(UserBO userBO);

}
