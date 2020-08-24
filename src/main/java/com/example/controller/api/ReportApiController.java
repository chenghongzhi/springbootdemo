package com.example.controller.api;

import com.example.model.Records;
import com.example.model.Report;
import com.example.service.RecordsService;
import com.example.service.ReportService;
import com.example.util.ResultJSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/report")
public class ReportApiController extends BaseController{
    @Autowired
    private ReportService reportService;
    @Autowired
    private RecordsService recordsService;

    @PostMapping("/add")
    public ResultJSON insert(@RequestBody Map<Object, Object> body){
        ResultJSON json = new ResultJSON();
        Report report = new Report();
        String content = (String) body.get("content");
        Integer filesId = (Integer) body.get("filesId");
        String type = (String) body.get("type");
        report.setStudentId(getUser().getId());
        report.setStudentName(getUser().getUsername());
        report.setInTime(new Date());
        report.setState(1);
        report.setContent(content);
        report.setFilesId(filesId);
        report.setType(Integer.parseInt(type));
        reportService.insert(report);
        json.success("新增报告成功");
        return json;
    }

    @PostMapping("/edit")
    public ResultJSON edit(@RequestBody Map<Object, Object> body){
        ResultJSON json = new ResultJSON();
        String id = (String) body.get("id");
        String suggestion = (String)body.get("teacherSuggestion");
        Integer state = (Integer)body.get("state");
        Report report = reportService.selectById(Integer.parseInt(id));
        if (report != null) {
            report.setTeacherId(getUser().getId());
            report.setTeacherName(getUser().getUsername());
            report.setTeacherSuggestion(suggestion);
            report.setState(state);
            reportService.update(report);
            Records records = new Records();
            records.setReportId(report.getId());
            records.setSuggestion(suggestion);
            records.setTime(new Date());
            records.setTypeId(report.getType());
            records.setStateId(report.getState());
            records.setUserId(report.getStudentId());
            recordsService.insert(records);
            json.success();
        } else {
            json.failure("内容不存在");
        }
        return json;
    }

}
