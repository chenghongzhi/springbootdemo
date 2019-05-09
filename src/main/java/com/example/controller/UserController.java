package com.example.controller;


import com.example.model.Role;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.UserService;
import com.sun.management.OperatingSystemMXBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import java.lang.management.ManagementFactory;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2019-04-27
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController{
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    private Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/login")
    public String login(){
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            return "redirect:/user/index";
        }
        return "login";
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("username", getUser());
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username, @RequestParam("password")String password, Model model){
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        Subject subject= SecurityUtils.getSubject();
        try {
            if (!subject.isAuthenticated()) {
                //进行验证，这里可以捕获异常，然后返回对应信息
                subject.login(token);
            }
        } catch (AuthenticationException e) {
            log.error(e.getMessage());
            return "redirect:/user/login";
        }
        return "index";
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject=SecurityUtils.getSubject();
        if (subject!=null) {
            subject.logout();
        }
        return "login";
    }

    @RequiresPermissions("user:list")
    @GetMapping("/list")
    public String list(Model model){
        List<User> users=userService.selectAll();
        model.addAttribute("users",users);
        return "user/user";
    }

    @RequiresPermissions("user:add")
    @GetMapping("/add")
    public String add(Model model){
        List<Role> role=roleService.selectAll();
        model.addAttribute("roles",role);
        return "user/add";
    }

    @RequiresPermissions("user:add")
    @PostMapping("/add")
    public String add(User user){
        userService.insert(user);
        return "redirect:/user/list";
    }

    @RequiresPermissions("user:edit")
    @GetMapping("/edit")
    public String edit(Integer id,Model model){
        model.addAttribute("roles",roleService.selectAll());
        model.addAttribute("user",userService.selectById(id));
        return "user/edit";
    }

    @RequiresPermissions("user:edit")
    @PostMapping("/edit")
    public String edit(User user){
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(null);
            user.setInTime(new Date());
        } else {
            ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
            String password = new SimpleHash("MD5", user.getPassword(), credentialsSalt, 1024).toString();
            user.setPassword(password);
            user.setInTime(new Date());
        }
        userService.update(user);
        return "redirect:/user/list";
    }

    @RequiresPermissions("user:delete")
    @GetMapping("/delete")
    public String delete(Integer id){
        userService.delete(id);
        return "redirect:/user/list";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(){
        return "unauthorized";
    }



}
