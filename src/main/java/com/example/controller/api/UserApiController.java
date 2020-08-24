package com.example.controller.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.UserService;
import com.example.util.ResultJSON;
import com.example.util.SessionManage;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.*;
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
    private Logger logger = LoggerFactory.getLogger(UserApiController.class);

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
//        String emailRule =  "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
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

    /**
     * 导入excel
     * @param
     * @param request
     * @return
     */
    @PostMapping("/userExcelImport")
    @RequiresPermissions("user:add")
    public ResultJSON uploadUserExcel(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        ResultJSON json = new ResultJSON();
        if (file.isEmpty()) {
            return json.failure("文件为空!");
        }
        try {
            XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
            //根据页面index 获取sheet页
            XSSFSheet sheet = wb.getSheetAt(0);
            //实体类集合
            List<User> importDatas = new ArrayList<>();
            XSSFRow row = null;
            //循环sesheet页中数据从第二行开始，第一行是标题
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                //获取每一行数据
                row = sheet.getRow(i);
                for (int j = 0; j<4 ;j++) {
                    row.getCell(j).setCellType(CellType.STRING);
                }
                User user = new User();
                if(row.getCell(0)!=null){
                    user.setUsername(row.getCell(0).getStringCellValue());
                }
                if(row.getCell(1)!=null){
                    user.setPassword(row.getCell(1).getStringCellValue());
                }
                if(row.getCell(2)!=null){
                    user.setRealname(row.getCell(2).getStringCellValue());
                }
                String roleName = row.getCell(3).getStringCellValue();
                Integer roleId = roleService.selectByName(roleName);
                user.setRoleId(roleId);
                user.setInTime(new Date());
                importDatas.add(user);
            }
            for (User user : importDatas) {
                userService.insert(user);
            }
            json.success("导入成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.failure("excel表中数据存在错误，导入失败!");
        }
        return json;
    }


    // 文件下载相关代码
    @GetMapping("/download/cf2e2b4f-2d7b-4d5f-8417-b5b0a1f5749a")
    public String downloadExcel(HttpServletRequest request, HttpServletResponse response) {
        String fileName = "1.xlsx";
        if (fileName != null) {
            //设置文件路径
            File file = new File(path , fileName);
            if (file.exists()) {
                response.setContentType("application/octet-stream");
                response.setHeader("content-type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);// 设置文件名
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    logger.info("文件下载成功");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return null;
    }



    @GetMapping(value = "/export")
    public void export(HttpServletResponse response) throws IOException {
        List<User> users = userService.selectStudent();

        HSSFWorkbook wb = new HSSFWorkbook();

        HSSFSheet sheet = wb.createSheet("获取excel测试表格");

        HSSFRow row = null;

        row = sheet.createRow(0);//创建第一个单元格
        row.setHeight((short) (26.25 * 20));
        row.createCell(0).setCellValue("用户信息列表");//为第一行单元格设值

        /*为标题设计空间
         * firstRow从第1行开始
         * lastRow从第0行结束
         *
         *从第1个单元格开始
         * 从第3个单元格结束
         */
        CellRangeAddress rowRegion = new CellRangeAddress(0, 0, 0, 2);
        sheet.addMergedRegion(rowRegion);

      /*CellRangeAddress columnRegion = new CellRangeAddress(1,4,0,0);
      sheet.addMergedRegion(columnRegion);*/


        /*
         * 动态获取数据库列 sql语句 select COLUMN_NAME from INFORMATION_SCHEMA.Columns where table_name='user' and table_schema='test'
         * 第一个table_name 表名字
         * 第二个table_name 数据库名称
         * */
        row = sheet.createRow(1);
        row.setHeight((short) (22.50 * 20));//设置行高
        row.createCell(0).setCellValue("用户Id");//为第一个单元格设值
        row.createCell(1).setCellValue("用户名");//为第二个单元格设值
        row.createCell(2).setCellValue("用户密码");//为第三个单元格设值

        for (int i = 0; i < users.size(); i++) {
            row = sheet.createRow(i + 2);
            User user = users.get(i);
            row.createCell(0).setCellValue(user.getId());
            row.createCell(1).setCellValue(user.getUsername());
            row.createCell(2).setCellValue(user.getPassword());
        }
        sheet.setDefaultRowHeight((short) (16.5 * 20));
        //列宽自适应
        for (int i = 0; i <= 13; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        OutputStream os = response.getOutputStream();
        response.setHeader("Content-disposition", "attachment;filename=user.xls");//默认Excel名称
        wb.write(os);
        os.flush();
        os.close();
    }
}
