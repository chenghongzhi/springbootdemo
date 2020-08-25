package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import com.example.util.JwtUtil;
import com.example.util.ResultJSON;
import com.example.util.SessionManage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/api/v1/")
public class UserApiController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private SessionManage sessionManage;
    @Value("${authCode.tokenKey}")
    private String AUTH_CODE_tokenKey;
    @Value("${authCode.captchaKey}")
    private String AUTH_CODE_captchaKey;
    private static final Logger logger = LoggerFactory.getLogger(UserApiController.class);

    @PostMapping("/login")
    public ResultJSON login(@RequestBody Map<String, String> body){
        ResultJSON json =new ResultJSON();
        Subject subject= SecurityUtils.getSubject();
        String username=body.get("username");
        User user = userService.selectByName(username);
        String password=body.get("password");
        Map<String,Object> map = new HashMap<>();
        //        String captcha=body.get("captcha");
//        String codeToken=request.getHeader("codeToken");
//        String redisCaptcha = sessionManage.redisGetValue(AUTH_CODE_captchaKey + ":" + codeToken);
//        if (redisCaptcha == null) {
//            json.failure("验证码已过期，请重新刷新验证码");
//            return json;
//        }
//        if (!redisCaptcha.equalsIgnoreCase(captcha)) {
//            json.failure("验证码输入错误,请重新输入");
//            return json;
//        }
        if (user == null) {
            json.failure("账号不存在,请输入正确的账号！");
            return json;
        }
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        try{
            subject.login(token);
//            String userToken = JwtUtil.sign(username);
            String userToken = (String) subject.getSession().getId();
            int logoutTime = 30;
            subject.getSession().setTimeout(logoutTime * 60 * 1000);
            map.put("token", userToken);
            map.put("userInfo",username);
            map.put("userId",getUser().getId());
//            sessionManage.redisSetValue(AUTH_CODE_tokenKey + ":" + userToken, username, logoutTime, TimeUnit.MINUTES);
//            sessionManage.redisDelete(AUTH_CODE_captchaKey + ":" + codeToken);
//            sessionManage.redisDelete("SHIRO_LOGIN_COUNT" + username);
            subject.getSession().setAttribute("user",username);
            json.success(map);
            logger.info("用户:" + username + "登录成功");
        } catch (UnknownAccountException e) {
            json.failure("用户账号不存在");
            logger.error("用户:"+username+"发生异常:"+e);
        } catch (Exception e){
            json.failure("用户账号/密码错误");
            logger.error("用户:"+username+"发生异常:"+e);
        }
        return json;
    }

    @GetMapping("/test")
    public ResultJSON test(){
        Subject subject= SecurityUtils.getSubject();
        String userToken = (String) subject.getSession().getId();
        return new ResultJSON().success(userToken);
    }


    @GetMapping("/logout")
    public ResultJSON logout(){
        ResultJSON json =new ResultJSON();
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        json.success("用户退出成功");
        return json;
    }

}
