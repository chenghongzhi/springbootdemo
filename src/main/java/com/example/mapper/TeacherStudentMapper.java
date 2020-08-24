package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.TeacherStudent;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherStudentMapper extends BaseMapper<TeacherStudent> {
    void insertForEach(@Param("lists") List<TeacherStudent> list);
}
