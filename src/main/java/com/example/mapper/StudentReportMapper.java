package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.StudentReport;

public interface StudentReportMapper extends BaseMapper<StudentReport> {
    StudentReport selectByCode(String code);
}
