package com.example.controller.admin;

import com.example.controller.api.BaseController;
import com.example.model.MyPage;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.RoleService;
import com.example.service.UserService;
import com.example.util.SessionManage;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private SessionManage sessionManage;
    @Autowired
    private RoleService roleService;

    @GetMapping("/add")
    @RequiresPermissions("user:add")
    public String userAdd(Integer id,Model model){
        List<Role> roleList =roleService.selectAll();
        model.addAttribute("roles",roleList);
        return "user/add";
    }

    @GetMapping("/userInfo")
    public String userInfo(Model model){
        User user = getUser();
        List<Role> roleList =roleService.selectAll();
        model.addAttribute("roles",roleList);
        model.addAttribute("user",user);
        return "user/userInfo";
    }

    @GetMapping("/edit")
    @RequiresPermissions("user:edit")
    public String edit(Integer id,Model model,Integer pageId){
        List<Role> roleList =roleService.selectAll();
        model.addAttribute("user",userService.selectById(id));
        model.addAttribute("roles",roleList);
        model.addAttribute("id",id);
        return "user/edit";
    }

    @PostMapping("/edit")
    @RequiresPermissions("user:edit")
    public String edit(@Valid User user, Integer id){
        userService.update(user);
        return "redirect:/userCenter/list";
    }

    @GetMapping("/list")
    @RequiresPermissions("user:list")
    public String list(Model model,@RequestParam(defaultValue = "0") Integer pageNo){
        MyPage<User> myPage = userService.selectAllByPage(pageNo);
        model.addAttribute("pages",myPage);
        return "/user/list";
    }

//    @GetMapping("/list/{typeID}/{pageNo}")
//    @RequiresPermissions("user:list")
//    public String userDiv(@PathVariable Integer typeID,@PathVariable Integer pageNo,Integer id,Model model) {
//        if (typeID==1){
//            if (id == 0) {
//                MyPage<User> userIPage = userService.selectAll(pageNo);
//                model.addAttribute("pages",userIPage);
//            } else if(id != null && id != 0){
//                Department department = departmentService.selectById(id);
//                if (department.getPId() == 0) {
//                    MyPage<User> userIPage = userService.selectAllByDepartmentId(pageNo,id);
//                    model.addAttribute("pages",userIPage);
//                } else {
//                    MyPage<User> userIPage = userService.selectAll(pageNo,id);
//                    model.addAttribute("pages",userIPage);
//                }
//            }
//            model.addAttribute("id",id);
//        }
//        if (typeID==2){
//            if (id == 0) {
//                MyPage<User> page = userService.selectAllWhiteHat(pageNo);
//                model.addAttribute("pages",page);
//            } else if(id != null && id != 0){
//                Department department = departmentService.selectById(id);
//                if (department.getPId() == 0) {
//                    MyPage<User> userIPage = userService.selectAllWhiteHatByDepartmentId(pageNo,id);
//                    model.addAttribute("pages",userIPage);
//                } else {
//                    MyPage<User> userIPage = userService.selectAllWhiteHatBydepartmentId(pageNo,id);
//                    model.addAttribute("pages",userIPage);
//                }
//            }
//            model.addAttribute("id",id);
//           }
//        model.addAttribute("typeID",typeID);
//        return "user/userDiv";
//    }
}
