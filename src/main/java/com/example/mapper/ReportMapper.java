package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.Report;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReportMapper extends BaseMapper<Report> {
    Report selectByCode(String code);
}
