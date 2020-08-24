package com.example.controller.admin;

import com.example.controller.api.BaseController;
import com.example.model.MyPage;
import com.example.model.Records;
import com.example.model.Report;
import com.example.service.RecordsService;
import com.example.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private RecordsService recordsService;

    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("name",getUser().getUsername());
        return "report/add";
    }

    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1") Integer pageNo, Model model){
        MyPage<Report> myPage = new MyPage<>(pageNo,10);
        switch (getUser().getRoleId()) {
            case 1:
                myPage=reportService.selectByTeacherId(pageNo,getUser().getId());
                break;
            case 2:
                myPage=reportService.selectByStudentId(pageNo,getUser().getId());
                break;
            case 3:
                myPage=reportService.selectByLeaderId(pageNo,getUser().getId());
                break;
            case 4:
                myPage=reportService.selectByPage(pageNo);
                break;
        }
        model.addAttribute("page",myPage);
        return "report/list";
    }

    @GetMapping("/detail")
    public String detail(Integer id, Model model){
        Report report = reportService.selectById(id);
        List<Records> recordsList = recordsService.selectAllRecordsByReportId(report.getStudentId(),report.getType());
        model.addAttribute("recordsList",recordsList);
        model.addAttribute("report",report);
        return "report/detail";
    }

}
