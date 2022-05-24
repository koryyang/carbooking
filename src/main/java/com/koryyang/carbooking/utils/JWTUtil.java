package com.koryyang.carbooking.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.koryyang.carbooking.model.bo.user.UserBO;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

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
                .withClaim("userId", userBO.getUserId())
                .withClaim("role", userBO.getRole())
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
            userBO.setUserId(decodedJWT.getClaim("tenantId").asString());
            userBO.setRole(decodedJWT.getClaim("role").asString());
            return userBO;
        } catch (Exception ex) {
            log.error("incorrect jwt");
            return null;
        }
    }

}
