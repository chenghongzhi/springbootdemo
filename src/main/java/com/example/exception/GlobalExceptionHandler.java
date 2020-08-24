package com.example.exception;

import com.example.util.ResultJSON;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Autowired
    private ResultJSON json;

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultException(Exception e){
        log.error(e.getMessage());
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }

    // 处理没有权限的异常
    @ExceptionHandler(value = UnauthorizedException.class)
    public ModelAndView unauthorizedException(HttpServletRequest request, Exception e){
        log.error(e.getMessage());
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("unauthorized");
        return modelAndView;
    }



}
