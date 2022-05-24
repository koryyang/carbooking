package com.koryyang.carbooking.framework.interceptor;

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
                // exclude register api
                .excludePathPatterns("/api/v1/user/register")
                // exclude login api
                .excludePathPatterns("/api/v1/user/login");
    }

}
