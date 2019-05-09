package com.example.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;


//@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
    private Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

    @ExceptionHandler(value = Exception.class)
    public ModelAndView defaultException(HttpServletRequest request, Exception e){
        log.error(e.getMessage());
        log.error("{}",e);
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.addObject("errorCode",getStatus(request));
        modelAndView.setViewName("error/error");
        System.out.println("异常处理");
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
