package com.koryyang.carbooking.interceptor;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * config interceptor
 * @author yanglingyu
 * @date 2022/5/23
 */
@Configuration
@AllArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    /**
     * authentication interceptor
     */
    private final AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // register authenticationInterceptor
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                // exclude get password rsa public key api
                .excludePathPatterns("/api/v1/user/public_key")
                // exclude register api
                .excludePathPatterns("/api/v1/user/register")
                // exclude login api
                .excludePathPatterns("/api/v1/user/login")
                // exclude car query api
                .excludePathPatterns("/api/v1/car/query");
    }

}
