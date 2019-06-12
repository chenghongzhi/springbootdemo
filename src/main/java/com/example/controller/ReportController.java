package com.example.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.model.Report;
import com.example.model.StudentReport;
import com.example.service.ReportService;
import com.example.service.StudentReportService;
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
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/report")
public class ReportController extends BaseController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private FileUtil fileUtil;
    @Autowired
    private StudentReportService studentReportService;
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
    public String add(
            @RequestParam("mainBody") MultipartFile mainBody,
            @RequestParam("assignmentBook") MultipartFile assignmentBook,
            @RequestParam("proposal") MultipartFile proposal,
            @RequestParam("file") MultipartFile file,
            @RequestParam("jieTiDaBian") MultipartFile jieTiDaBian,
            @RequestParam("finalReport") MultipartFile finalReport,
            @RequestParam("production") MultipartFile production,
            @RequestParam("reply") MultipartFile reply,
            Report report,Model model){
        StudentReport studentReport=new StudentReport();
        report.setStudentId(getUser().getId());
        reportService.insert(report);
        if (mainBody.isEmpty() && assignmentBook.isEmpty()&& proposal.isEmpty()
                && file.isEmpty() && jieTiDaBian.isEmpty() && finalReport.isEmpty()
                && production.isEmpty() &&reply.isEmpty()) {
            log.error("文件为空");
            model.addAttribute("message","文件为空");
            return "error";
        }
        if (!mainBody.isEmpty() && mainBody != null) {
            String fileName=mainBody.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName =fileUtil.uploadFile(mainBody,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("正文");
            studentReportService.insert(studentReport);
        }
        if (!assignmentBook.isEmpty() && assignmentBook != null) {
            String fileName=assignmentBook.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName =fileUtil.uploadFile(assignmentBook,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("任务书");
            studentReportService.insert(studentReport);
        }
        if (!proposal.isEmpty() && proposal != null) {
           String fileName=proposal.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName =fileUtil.uploadFile(proposal,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("开题报告");
            studentReportService.insert(studentReport);
        }
        if (!file.isEmpty() && file != null) {
            String fileName = file.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName=fileUtil.uploadFile(file,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("中检报告");
            studentReportService.insert(studentReport);
            log.info("文件上传成功");
        }
        if (!jieTiDaBian.isEmpty() && jieTiDaBian != null) {
            String fileName = jieTiDaBian.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName=fileUtil.uploadFile(jieTiDaBian,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("结题答辩");
            studentReportService.insert(studentReport);
            log.info("文件上传成功");
        }
        if (!finalReport.isEmpty() && finalReport != null) {
            String fileName = finalReport.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName = fileUtil.uploadFile(finalReport,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("结题报告");
            studentReportService.insert(studentReport);
            log.info("文件上传成功");
        }
        if (!production.isEmpty() && production != null) {
            String fileName = production.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName = fileUtil.uploadFile(production,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("结题答辩");
            studentReportService.insert(studentReport);
            log.info("文件上传成功");
        }
        if (!reply.isEmpty() && reply != null) {
            String fileName = reply.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName = fileUtil.uploadFile(reply,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("答辩报告");
            studentReportService.insert(studentReport);
            log.info("文件上传成功");
        }
        return "redirect:/report/list";
    }

    @RequiresPermissions("report:edit")
    @GetMapping("/edit")
    public String edit(Integer id,Model model){
        model.addAttribute("report",reportService.selectById(id));
//        model.addAttribute("list",studentReportService.selectByReportId(id));
        return "report/edit";
    }

    @RequiresPermissions("report:edit")
    @PostMapping("/edit")
    public String edit(@RequestParam("proposal") MultipartFile proposal,
                       @RequestParam("file") MultipartFile file,
                       @RequestParam("finalReport") MultipartFile finalReport,
                       @RequestParam("reply") MultipartFile reply,
                       Report report,Model model){
        StudentReport studentReport=new StudentReport();
        report.setStudentId(getUser().getId());
        reportService.update(report);
        if (!proposal.isEmpty() && proposal != null) {
            String fileName=proposal.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName = fileUtil.uploadFile(proposal,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("开题报告");
            if (studentReportService.selectByReportId(report.getId(),"开题报告").isEmpty()){
                studentReportService.insert(studentReport);
            }
            studentReportService.update(studentReport);
        }
        if (!file.isEmpty() && file != null) {
            String fileName = file.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName = fileUtil.uploadFile(file,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("中检报告");
            if (studentReportService.selectByReportId(report.getId(),"中检报告").isEmpty()){
                studentReportService.insert(studentReport);
            }
            studentReportService.update(studentReport);
            log.info("文件上传成功");
        }
        if (!finalReport.isEmpty() && finalReport != null) {
            String fileName = finalReport.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName =fileUtil.uploadFile(finalReport,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("结题报告");
            if (studentReportService.selectByReportId(report.getId(),"结题报告").isEmpty()){
                studentReportService.insert(studentReport);
            }
            studentReportService.update(studentReport);
            log.info("文件上传成功");
        }
        if (!reply.isEmpty() && reply != null) {
            String fileName = reply.getOriginalFilename();
            String fileNameSuffix = fileName.substring(fileName.lastIndexOf(".") + 1);
            String Suffix = "pdf/docx/doc/pptx/ppt/txt/zip/tar/rar";
            if (Suffix.indexOf(fileNameSuffix) < 0) {
                log.error("文件上传格式有误");
                model.addAttribute("message","文件上传格式有误");
                return "error";
            }
            fileName = fileUtil.uploadFile(reply,fileName,path);
            studentReport.setCode((UUID.randomUUID().toString().replace("-", "").toLowerCase()));
            studentReport.setFileurl(static_url+fileName);
            studentReport.setFilepath(path+fileName);
            studentReport.setUrl("/report/download/"+studentReport.getCode());
            studentReport.setReportId(report.getId());
            studentReport.setStudentId(report.getStudentId());
            studentReport.setReportType("答辩报告");
            if (studentReportService.selectByReportId(report.getId(),"答辩报告").isEmpty()){
                studentReportService.insert(studentReport);
            }
            studentReportService.update(studentReport);
            log.info("文件上传成功");
        }
        return "redirect:/report/list";
    }

    @RequiresPermissions("report:check")
    @GetMapping("/check")
    public String check(Integer id,Model model){
        List<StudentReport> studentReportList=studentReportService.selectByReportId(id);
        model.addAttribute("list",studentReportList);
        model.addAttribute("report",reportService.selectById(id));
        model.addAttribute("name",getUser().getUsername());
        return "report/check";
    }

    @RequiresPermissions("report:check")
    @PostMapping("/check")
    public String check(Report report,Integer pass){
        if (pass == 1){
            report.setStatus("通过");
        } else {
            report.setStatus("不通过");
        }
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
        fileUtil.removeFile(id);
        reportService.delete(id);
        studentReportService.deleteByReportId(id);
        return "redirect:/report/list";
    }


}
