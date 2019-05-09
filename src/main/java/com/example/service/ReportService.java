package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.ReportMapper;
import com.example.model.Report;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;

    public void insert(Report report){
        report.setInTime(new Date());
        report.setStatus("未查看");
        reportMapper.insert(report);
    }

    public void delete(Integer id){
        reportMapper.deleteById(id);
    }

    public Report selectById(Integer id){
        return reportMapper.selectById(id);
    }

    public Report selectByCode(String code){
        return reportMapper.selectByCode(code);
    }

    public List<Report> selectAllStudent(String name){
        QueryWrapper<Report> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(Report::getStudentName,name);
        wrapper.orderByAsc("id");
        return reportMapper.selectList(wrapper);
    }

    public List<Report> selectAllTeacher(String name){
        QueryWrapper<Report> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(Report::getTeacherName,name);
        return reportMapper.selectList(wrapper);
    }

    public List<Report> selectAll(){
        QueryWrapper<Report> wrapper=new QueryWrapper<>();
        wrapper.orderByAsc("status");
        return reportMapper.selectList(wrapper);
    }

    public void update(Report report){
        report.setInTime(new Date());
        reportMapper.updateById(report);
    }
}
