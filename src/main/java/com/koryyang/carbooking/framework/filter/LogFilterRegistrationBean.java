package com.koryyang.carbooking.framework.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;

/**
 * log filter, to log for each request
 * @author yanglingyu
 * @date 2022/5/23
 */
@Component
@Slf4j
public class LogFilterRegistrationBean extends FilterRegistrationBean<Filter> {

    @PostConstruct
    public void init() {
        setOrder(1);
        setFilter(new LogFilter());
        setUrlPatterns(Collections.singleton("/**"));
    }

    private static class LogFilter implements Filter {

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            log.info("访问者IP：{}，请求路径：{}，请求方法：{}", ((HttpServletRequest) servletRequest).getHeader("remote_addr"), ((HttpServletRequest) servletRequest).getRequestURI(), ((HttpServletRequest) servletRequest).getMethod());
            filterChain.doFilter(servletRequest, servletResponse);
        }

    }



}
