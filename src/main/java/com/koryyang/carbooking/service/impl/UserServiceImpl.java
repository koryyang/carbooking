package com.koryyang.carbooking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.koryyang.carbooking.exception.BusinessException;
import com.koryyang.carbooking.exception.SystemException;
import com.koryyang.carbooking.mapper.UserMapper;
import com.koryyang.carbooking.model.bo.user.UserBO;
import com.koryyang.carbooking.model.entity.UserEntity;
import com.koryyang.carbooking.model.request.tenant.UserLoginRequest;
import com.koryyang.carbooking.model.request.tenant.UserRegisterRequest;
import com.koryyang.carbooking.model.vo.tenant.UserLoginVO;
import com.koryyang.carbooking.service.UserService;
import com.koryyang.carbooking.utils.JWTUtil;
import com.koryyang.carbooking.utils.PasswordUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author yanglingyu
 * @date 2022/5/23
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper tenantMapper;

    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public void register(UserRegisterRequest request) {
        Long count = tenantMapper.selectCount(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount, request.getAccount()));
        if (count > 0) {
            throw new BusinessException("account has existed");
        }
        UserEntity entity = new UserEntity();
        entity.setAccount(request.getAccount());
        String encryptedPassword = request.getEncryptedPassword();
        String password = PasswordUtil.rsaDecrypted(encryptedPassword);
        entity.setHashPassword(PasswordUtil.hashPassword(password));
        entity.setPhone(request.getPhone());
        int insert = tenantMapper.insert(entity);
        if (insert != 1) {
            throw new SystemException("注册失败");
        }
    }

    @Override
    public UserLoginVO login(UserLoginRequest request) {
        UserEntity entity = tenantMapper.selectOne(new QueryWrapper<UserEntity>().lambda()
                .eq(UserEntity::getAccount, request.getAccount()));
        if (entity == null) {
            throw new BusinessException("tenant not exist");
        }
        if (!PasswordUtil.checkPassword(request.getEncryptedPassword(), entity.getHashPassword())) {
            throw new BusinessException("password not correct");
        }
        UserBO bo = new UserBO();
        bo.setUserId(entity.getId());
        bo.setRole(entity.getRole());
        UserLoginVO vo = new UserLoginVO();
        vo.setUserId(entity.getId());
        vo.setRole(entity.getRole());
        vo.setToken(JWTUtil.encodeUser(bo));
        return vo;
    }

    @Override
    public void logout(UserBO userBO) {
        stringRedisTemplate.delete(userBO.getUserId());
    }
}
