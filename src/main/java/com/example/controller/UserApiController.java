package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.UserService;
import com.example.util.ResultJSON;
import com.example.util.SessionManage;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/user")
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

    @Autowired
    private RoleService roleService;
    @Value("${filepath}")
    private String path;

    @PostMapping("/users")
    @RequiresPermissions("user:add")
    public ResultJSON addUser(@RequestBody @Valid User user, BindingResult result){
        ResultJSON json =new ResultJSON();
        Integer minLength=6;
        Integer maxLength=14;
        String pattern1 = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W])[\\da-zA-Z\\W]{%s,%s}$";
        String emailRule =  "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        String format = String.format(pattern1,minLength,maxLength);
        if (result.hasErrors()) {
        result.getAllErrors().forEach(err -> {
            json.failure(err.getDefaultMessage());
        });
        return json;
        }
        if (!Pattern.matches(format,user.getPassword())) {
            String warning="密码复杂度不够,需要包含字母,字符和数字字符长度在%s到%s位之间!";
            String message = String.format(warning,minLength,maxLength);
            json.failure(message);
            return json;
        }
        if (user != null) {
            if (userService.accountAvailable(user.getUsername())) {
                userService.insert(user);
                json.success("新增用户成功");
                logger.info("新增用户:"+user.getUsername()+":成功");
            } else {
                json.failure("该账号已被注册!");
            }
        } else {
            json.failure("新增用户失败，请重试");
            logger.error("新增用户失败，请重试");
        }
        return json;
    }


    @GetMapping("/users")
    @RequiresPermissions("user:list")
    public ResultJSON showAllUser(@RequestParam(defaultValue = "1") Integer pageNo){
        ResultJSON json =new ResultJSON();
        IPage<User> page=userService.selectAllByPage(pageNo);
        Map<String,Object> map = new HashMap<>();
        map.put("usersInfo",page.getRecords());
        map.put("currentSize",page.getCurrent());
        map.put("totalSize",page.getTotal());
        map.put("totalPages",page.getPages());
        json.success(map);
        logger.info("查询所有用户");
        return json;
    }


    @GetMapping("/users/{id}")
    @RequiresPermissions("user:delete")
    public ResultJSON deleteUser(@PathVariable Integer id){
        ResultJSON json =new ResultJSON();
        User user = userService.selectById(id);
        if (user!=null) {
            userService.delete(id);
            json.success("删除成功");
            logger.info("删除用户:"+user.getUsername()+":成功");
        } else {
            json.failure("用户不存在");
        }
        return json;
    }

    @PostMapping("/updateInfo")
    public ResultJSON updateInfo(@RequestBody @Valid User user, BindingResult result) {
        ResultJSON json =new ResultJSON();
        Integer minLength=6;
        Integer maxLength=14;
        String pattern1 = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W])[\\da-zA-Z\\W]{%s,%s}$";
        String emailRule =  "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        String format = String.format(pattern1,minLength,maxLength);
        if (!StringUtils.isEmpty(user.getPassword()) && !Pattern.matches(format,user.getPassword())) {
            String warning="密码复杂度不够,需要包含字母，字符和数字且长度在%s到%s位之间!";
            String message = String.format(warning,minLength,maxLength);
            json.failure(message);
            return json;
        }
        if (user!=null) {
            if (StringUtils.isEmpty(user.getPassword())) {
                user.setPassword(null);
                userService.updateUser(user);
            } else {
                userService.update(user);
            }
            json.success("用户更新信息成功");
        } else {
            json.failure("用户更新信息失败");
        }
        return json;
    }



    @PostMapping("/updateUsers")
    @RequiresPermissions("user:edit")
    public ResultJSON updateCourse(@RequestBody @Valid User user, BindingResult result) {
        ResultJSON json =new ResultJSON();
        Integer minLength=6;
        Integer maxLength=14;
        String pattern1 = "^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[\\W])[\\da-zA-Z\\W]{%s,%s}$";
        String emailRule =  "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
        String format = String.format(pattern1,minLength,maxLength);
        if (!StringUtils.isEmpty(user.getPassword()) && !Pattern.matches(format,user.getPassword())) {
            String warning="密码复杂度不够,需要包含字母，字符和数字且长度在%s到%s位之间!";
            String message = String.format(warning,minLength,maxLength);
            json.failure(message);
            return json;
        }
        if (user!=null) {
            if (StringUtils.isEmpty(user.getPassword())) {
                user.setPassword(null);
                userService.updateUser(user);
            } else {
                userService.update(user);
            }
            json.success("用户更新信息成功");
        } else {
            json.failure("用户更新信息失败");
        }
        return json;
    }


    @PostMapping("/login")
    public ResultJSON login(@RequestBody Map<String, String> body, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.getAttribute("code");
        ResultJSON json =new ResultJSON();
        Subject subject= SecurityUtils.getSubject();
        String username=body.get("username");
        User user = userService.selectByName(username);
        String password=body.get("password");
        String captcha=body.get("captcha");
        Map<String,Object> map = new HashMap<>();
        String codeToken=request.getHeader("codeToken");
        String redisCaptcha = sessionManage.redisGetValue(AUTH_CODE_captchaKey + ":" + codeToken);
        if (redisCaptcha == null) {
            json.failure("验证码已过期，请重新刷新验证码");
            return json;
        }
        if (!redisCaptcha.equalsIgnoreCase(captcha)) {
            json.failure("验证码输入错误,请重新输入");
            return json;
        }
        if (user == null) {
            json.failure("账号不存在,请输入正确的账号！");
            return json;
        }
        UsernamePasswordToken token=new UsernamePasswordToken(username,password);
        try{
            subject.login(token);
            Integer logoutTime = 30;
            subject.getSession().setTimeout(logoutTime * 60 * 1000);
            String userToken= SessionManage.generateToken();
            map.put("token", userToken);
            map.put("userInfo",username);
            map.put("userId",getUser().getId());
            sessionManage.redisSetValue(AUTH_CODE_tokenKey + ":" + userToken, username, logoutTime, TimeUnit.MINUTES);
            sessionManage.redisDelete(AUTH_CODE_captchaKey + ":" + codeToken);
            sessionManage.redisDelete("SHIRO_LOGIN_COUNT" + username);
            session.setAttribute("name",username);
            session.setAttribute("role",user.getRoleId());
            subject.getSession().setAttribute("user",username);
            json.success(map);
            logger.info("用户:" + username + "登录成功");
        } catch (UnknownAccountException e) {
            json.failure("用户账号不存在");
            logger.error("用户:"+username+"发生异常:"+e);
        } catch (IncorrectCredentialsException e){
            json.failure("用户账号/密码错误");
            logger.error("用户:"+username+"发生异常:"+e);
        } catch (Exception e){
            json.failure("用户账号/密码错误");
            logger.error("用户:"+username+"发生异常:"+e);
        }
        return json;
    }



    @GetMapping("/logout")
    public ResultJSON logout(HttpServletRequest request){
        ResultJSON json =new ResultJSON();
        String token=request.getHeader("Authorization");
        sessionManage.redisDelete(AUTH_CODE_tokenKey + ":" + token);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        logger.info("用户退出成功");
        return json;
    }

    @GetMapping(value = "/unauth")
    public ResultJSON unauth() {
        ResultJSON json =new ResultJSON();
        json.failure("未登录");
        return json;
    }


}
