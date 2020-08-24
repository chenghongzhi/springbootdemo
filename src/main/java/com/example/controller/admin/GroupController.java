package com.example.controller.admin;

import com.example.model.MyPage;
import com.example.model.User;
import com.example.service.TeacherStudentService;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/group")
public class GroupController {
    @Autowired
    private UserService userService;

    @RequestMapping("/list")
    public String list(Model model, @RequestParam(defaultValue = "0") Integer pageNo){
        MyPage<User> myPage = userService.selectAllGroupLeaderByPage(pageNo);
        model.addAttribute("pages",myPage);
        return "/group/list";
    }

    @RequestMapping("/edit")
    public String edit(Integer id,Model model){
        List<User> userList = userService.selectTeacher();
        model.addAttribute("id",id);
        model.addAttribute("list",userList);
        return "/group/edit";
    }
}
