package com.koryyang.carbooking.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.koryyang.carbooking.constant.UserConstant;
import com.koryyang.carbooking.exception.BusinessException;
import com.koryyang.carbooking.model.bo.user.UserBO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

import java.util.Date;

/**
 * JWT Util
 * @author yanglingyu
 * @date 2022/5/23
 */
@Slf4j
public class JWTUtil {

    /**
     * secret of jwt
     */
    private static final String JWT_SECRET = "DG*(G$hf9hg9fq1h023h3#$%";

    /**
     * jwt expire in 2 hours
     */
    private static final long EXPIRE_TIME = 2 * 60 * 60 * 1000L;

    /**
     * sign
     * @param userBO userBo
     * @return jwt
     */
    @SneakyThrows
    public static String encodeUser(UserBO userBO) {
        return JWT.create()
                .withClaim(UserConstant.USER_ID, userBO.getUserId())
                .withClaim(UserConstant.ACCOUNT, userBO.getAccount())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_TIME))
                .sign(Algorithm.HMAC256(JWT_SECRET));
    }

    /**
     * verify jwt and decode into userBO
     * @param jwt jwt
     * @return userBO
     */
    @SneakyThrows
    public static UserBO verifyAndDecode(String jwt) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
        try {
            DecodedJWT decodedJWT = verifier.verify(jwt);
            UserBO userBO = new UserBO();
            userBO.setUserId(decodedJWT.getClaim(UserConstant.USER_ID).asString());
            userBO.setAccount(decodedJWT.getClaim(UserConstant.ACCOUNT).asString());
            Assert.hasText(userBO.getUserId(), "user id missing");
            Assert.hasText(userBO.getAccount(), "account missing");
            return userBO;
        } catch (TokenExpiredException ex) {
            throw new BusinessException("token expired");
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw new BusinessException("incorrect token");
        }
    }

}
