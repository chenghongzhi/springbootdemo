package com.example.controller.api;

import com.example.model.User;
import com.example.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {
    @Autowired
    private UserService userService;
    protected User getUser() {
        Subject subject = SecurityUtils.getSubject();
        String principal = (String) subject.getPrincipal();
        User user=userService.selectByName(principal);
        return user;
    }
}
