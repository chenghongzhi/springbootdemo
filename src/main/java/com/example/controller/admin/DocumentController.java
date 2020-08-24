package com.example.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.model.ReportFiles;
import com.example.service.ReportFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private ReportFilesService reportFilesService;

    @GetMapping("/add")
    public String add(){
        return "course/add";
    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNo, Model model){
        IPage<ReportFiles> list = reportFilesService.selectAllFiles(pageNo);
        model.addAttribute("list",list);
        return "course/list";
    }

}
