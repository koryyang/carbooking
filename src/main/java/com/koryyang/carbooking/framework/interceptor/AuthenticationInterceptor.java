package com.koryyang.carbooking.framework.interceptor;

import com.koryyang.carbooking.exception.BusinessException;
import com.koryyang.carbooking.model.bo.user.UserBO;
import com.koryyang.carbooking.utils.JWTUtil;
import com.koryyang.carbooking.utils.ServletUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * authentication interceptor
 * @author yanglingyu
 * @date 2022/5/23
 */
@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new BusinessException("token is missing");
        }
        UserBO userBO = JWTUtil.verifyAndDecode(token);
        if (userBO == null) {
            throw new BusinessException("incorrect token");
        }
        Boolean hasKey = redisTemplate.hasKey(userBO.getUserId());
        if (hasKey == null || !hasKey) {
            throw new BusinessException("incorrect token");
        }
        // set user info of current thread
        ServletUtil.setCurrentTenant(userBO);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // remove user info of current thread
        ServletUtil.removeCurrentUser();
    }
}
