package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.model.MyPage;
import com.example.model.Report;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ReportMapper extends BaseMapper<Report> {
    MyPage<Report> selectAll(@Param("pg") MyPage<Report> myPage);

    MyPage<Report> selectByPage(@Param("pg") MyPage<Report> myPage);

    MyPage<Report> selectByTeacherId(@Param("pg") MyPage<Report> myPage);

    MyPage<Report> selectByLeaderId(@Param("pg") MyPage<Report> myPage);

    MyPage<Report> selectByStudentId(@Param("pg") MyPage<Report> myPage);

    Report selectById(Integer id);
}
