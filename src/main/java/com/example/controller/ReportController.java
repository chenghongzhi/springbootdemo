package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.model.Report;
import com.example.service.ReportService;
import com.example.util.FileUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private FileUtil fileUtil;
    private Logger log= LoggerFactory.getLogger(ReportController.class);
    @Value("${spring.path}")
    private String path;
    @Value("${spring.url}")
    private String static_url;

    @RequiresPermissions("report:list")
    @GetMapping("/list")
    public String list(@RequestParam(defaultValue = "1")Integer pageNo, Model model){
        if (getUser().getRoleId()==2) {
            model.addAttribute("pages",reportService.selectAllStudent(pageNo,getUser().getUsername()));
        } else {
            IPage<Report> iPage=reportService.selectAllByPage(pageNo);
            model.addAttribute("pages",iPage);
        }
        return "report/list";
    }

    @RequiresPermissions("report:add")
    @GetMapping("/add")
    public String add(Model model){
        model.addAttribute("name",getUser().getUsername());
        return "report/add";
    }

    @RequiresPermissions("report:add")
    @PostMapping("/add")
    public String add(@RequestParam("file") MultipartFile file,Report report){
        if (file.isEmpty() || file == null) {
            log.error("文件为空！");
            return null;
        }
        String fileName = file.getOriginalFilename();
        String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String Suffix = "pdf/docx/doc";
        if (Suffix.indexOf(fileNameSuffix) < 0) {
            log.error("文件上传格式有误");
            return null;
        }
        String filePath=fileUtil.uploadFile(file,fileName,path);
        report.setFilepath(filePath);
        report.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
        report.setFileurl("/report/download/"+report.getCode());
        reportService.insert(report);
        log.info("文件上传成功");
        return "redirect:/report/list";
    }

    @RequiresPermissions("report:edit")
    @GetMapping("/edit")
    public String edit(Integer id,Model model){
        model.addAttribute("report",reportService.selectById(id));
        return "report/edit";
    }

    @RequiresPermissions("report:edit")
    @PostMapping("/edit")
    public String edit(Report report){
        reportService.update(report);
        return "redirect:/report/list";
    }

    @RequiresPermissions("report:check")
    @GetMapping("/check")
    public String check(Integer id,Model model){
        model.addAttribute("report",reportService.selectById(id));
        model.addAttribute("name",getUser().getUsername());
        return "report/check";
    }

    @RequiresPermissions("report:check")
    @PostMapping("/check")
    public String check(Report report){
        report.setStatus("已查看");
        reportService.update(report);
        return "redirect:/report/list";
    }

    @GetMapping("/download/{code}")
    public void download(@PathVariable String code,HttpServletResponse response)throws UnsupportedEncodingException {
        fileUtil.downloadFile(response,code);
    }

    @RequiresPermissions("report:delete")
    @GetMapping("/delete")
    public String delete(Integer id){
        reportService.delete(id);
        return "redirect:/report/list";
    }


}
