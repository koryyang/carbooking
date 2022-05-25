package com.koryyang.carbooking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.koryyang.carbooking.constant.RedisConstant;
import com.koryyang.carbooking.exception.BusinessException;
import com.koryyang.carbooking.mapper.UserMapper;
import com.koryyang.carbooking.model.bo.user.UserBO;
import com.koryyang.carbooking.model.entity.UserEntity;
import com.koryyang.carbooking.model.request.user.UserLoginRequest;
import com.koryyang.carbooking.model.request.user.UserRegisterRequest;
import com.koryyang.carbooking.model.vo.user.UserLoginVO;
import com.koryyang.carbooking.service.UserService;
import com.koryyang.carbooking.utils.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * user service implementation
 * @author yanglingyu
 * @date 2022/5/23
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * user mapper
     */
    private final UserMapper userMapper;

    /**
     * redis
     */
    private final RedisTemplate<String, String> redisTemplate;

    /**
     * register
     * @param request request
     */
    @Override
    public void register(UserRegisterRequest request) {
        Long count = userMapper.selectCount(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount, request.getAccount()));
        if (count > 0) {
            throw new BusinessException("account has existed");
        }
        UserEntity entity = new UserEntity();
        entity.setAccount(request.getAccount());
        entity.setPassword(request.getPassword());
        userMapper.insert(entity);
    }

    /**
     * login
     * @param request request
     * @return login result
     */
    @Override
    public UserLoginVO login(UserLoginRequest request) {
        UserEntity entity = userMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount, request.getAccount()));
        if (entity == null) {
            throw new BusinessException("user not exist");
        }
        if (!entity.getPassword().equals(request.getPassword())) {
            throw new BusinessException("password not correct");
        }
        // login success
        UserBO bo = new UserBO();
        bo.setUserId(entity.getId());
        bo.setAccount(entity.getAccount());
        // generate token
        String token = JWTUtil.encodeUser(bo);
        // set token into redis
        redisTemplate.opsForValue().set(RedisConstant.USER_TOKEN_PREFIX + request.getAccount(), token, RedisConstant.DATA_EXPIRE_TIME, TimeUnit.HOURS);
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(entity.getId());
        vo.setToken(token);
        return vo;
    }

}
