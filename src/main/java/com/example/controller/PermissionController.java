package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController{

    @GetMapping("/list")
    public String list(Model model){
        model.addAttribute("name","test");
        return "permission/list";
    }

}
