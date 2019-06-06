package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.TeacherStudentMapper;
import com.example.mapper.TeacherStudentMapper;
import com.example.model.TeacherStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TeacherStudentService {
    @Autowired
    private TeacherStudentMapper teacherStudentMapper;

    public void insert(TeacherStudent studentReport){
        teacherStudentMapper.insert(studentReport);
    }

    public List<TeacherStudent> selectByTeacherId(Integer teacherId){
        QueryWrapper<TeacherStudent> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(TeacherStudent::getTeacherId,teacherId);
        return teacherStudentMapper.selectList(wrapper);
    }

    public void deleteByStudentId(Integer studentId){
        QueryWrapper<TeacherStudent> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq( TeacherStudent::getStudentId, studentId);
        teacherStudentMapper.delete(wrapper);
    }

    public void deleteByTeacherId(Integer teacherId){
        QueryWrapper<TeacherStudent> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq( TeacherStudent::getTeacherId, teacherId);
        teacherStudentMapper.delete(wrapper);
    }


}
