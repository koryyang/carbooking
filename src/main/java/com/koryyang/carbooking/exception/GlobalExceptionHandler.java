package com.koryyang.carbooking.exception;

import com.koryyang.carbooking.model.vo.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * global exception handler
 * @author yanglingyu
 * @date 2022/5/23
 */
@ControllerAdvice
@ResponseBody
@Slf4j
public class GlobalExceptionHandler {

    /**
     * handle system exception
     * @param ex system exception
     * @param request servlet request
     * @return exception response
     */
    @ExceptionHandler(SystemException.class)
    public Response<?> handleSystemException(SystemException ex, HttpServletRequest request) {
        log.error("request uri:{}", request.getRequestURI());
        log.error("exception message:{}", ex.getMessage());
        ex.printStackTrace();
        return Response.systemError();
    }

    /**
     * handle business exception
     * @param ex business exception
     * @return exception response
     */
    @ExceptionHandler(BusinessException.class)
    public Response<?> handlerBusinessException(BusinessException ex) {
        return Response.businessError(ex.getMessage());
    }

    /**
     * handle argument not valid exception
     * @param ex argument not valid exception
     * @return exception response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        String message = ex.getAllErrors().get(0).getDefaultMessage();
        return Response.businessError(message);
    }

    /**
     * handle illegal argument exception
     * @param ex illegal argument exception
     * @return exception response
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public Response<?> handleIllegalArgumentException(IllegalArgumentException ex) {
        String message = ex.getMessage();
        return Response.businessError(message);
    }

    /**
     * handle bind exception
     * @param ex bind exception
     * @return exception response
     */
    @ExceptionHandler(BindException.class)
    public Response<?> handleBindException(BindException ex) {
        String message = ex.getFieldErrors().get(0).getDefaultMessage();
        return Response.businessError(message);
    }

    /**
     * handle http message convert exception
     * @param ex http message convert exception
     * @return exception response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.warn("request uri:{}", request.getRequestURI());
        log.warn("exception message:{}", ex.getMessage());
        return Response.businessError("invalid param, please check");
    }

    /**
     * handle other exception
     * @param ex other exception
     * @param request servlet request
     * @return exception response
     */
    @ExceptionHandler(Exception.class)
    public Response<?> handleException(Exception ex, HttpServletRequest request) {
        log.error("request uri:{}", request.getRequestURI());
        log.error("exception message:{}", ex.getMessage());
        ex.printStackTrace();
        return Response.systemError();
    }

}
