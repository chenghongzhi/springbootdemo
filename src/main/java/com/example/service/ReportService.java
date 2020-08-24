package com.example.service;

import com.example.mapper.ReportMapper;
import com.example.model.MyPage;
import com.example.model.Report;
import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportService {
    @Autowired
    private ReportMapper reportMapper;

    public void insert(Report report){
        reportMapper.insert(report);
    }

    public void deleteById(Integer id){
        reportMapper.deleteById(id);
    }

    public Report selectById(Integer id){
        return reportMapper.selectById(id);
    }

    public void update(Report report){
        reportMapper.updateById(report);
    }

    public MyPage<Report> selectByPage(Integer pageNo){
        MyPage<Report> myPage = new MyPage<>(pageNo, 10);
        return reportMapper.selectByPage(myPage);
    }

    public MyPage<Report> selectByTeacherId(Integer pageNo,Integer teacherId){
        MyPage<Report> myPage = new MyPage<>(pageNo, 10);
        myPage.setSelectInt(teacherId);
        return reportMapper.selectByTeacherId(myPage);
    }

    public MyPage<Report> selectByLeaderId(Integer pageNo,Integer teacherId){
        MyPage<Report> myPage = new MyPage<>(pageNo, 10);
        myPage.setSelectInt(teacherId);
        return reportMapper.selectByLeaderId(myPage);
    }

    public MyPage<Report> selectByStudentId(Integer pageNo,Integer studentId){
        MyPage<Report> myPage = new MyPage<>(pageNo, 10);
        myPage.setSelectInt(studentId);
        return reportMapper.selectByStudentId(myPage);
    }


}
