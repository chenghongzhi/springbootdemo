package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.mapper.StudentReportMapper;
import com.example.model.StudentReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class StudentReportService {
    @Autowired
    private StudentReportMapper studentReportMapper;

    public void insert(StudentReport studentReport){
        studentReport.setInTime(new Date());
        studentReportMapper.insert(studentReport);
    }

    public List<StudentReport> selectByStudentId(Integer studentId){
        QueryWrapper<StudentReport> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(StudentReport::getStudentId,studentId);
        return studentReportMapper.selectList(wrapper);
    }

    public List<StudentReport> selectByReportId(Integer reportId){
        QueryWrapper<StudentReport> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(StudentReport::getReportId,reportId);
        return studentReportMapper.selectList(wrapper);
    }

    public List<StudentReport> selectByReportId(Integer reportId,String type){
        QueryWrapper<StudentReport> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(StudentReport::getReportId,reportId).eq(StudentReport::getReportType,type);
        return studentReportMapper.selectList(wrapper);
    }

    public void deleteByStudentId(Integer studentId){
        QueryWrapper<StudentReport> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq( StudentReport::getStudentId, studentId);
        studentReportMapper.delete(wrapper);
    }

    public void deleteByReportId(Integer reportId){
        QueryWrapper<StudentReport> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq( StudentReport::getReportId, reportId);
        studentReportMapper.delete(wrapper);
    }

    public StudentReport selectByCode(String code){
        return studentReportMapper.selectByCode(code);
    }

    public void update(StudentReport studentReport){
        UpdateWrapper<StudentReport> wrapper=new UpdateWrapper<>();
        wrapper.lambda().eq(StudentReport::getReportId,studentReport.getReportId()).eq(StudentReport::getReportType,studentReport.getReportType());
        studentReportMapper.update(studentReport,wrapper);
    }

}
