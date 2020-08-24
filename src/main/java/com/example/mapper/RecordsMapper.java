package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.Records;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RecordsMapper extends BaseMapper<Records> {
    List<Records> selectAllRecordsByReportId(Integer userId,Integer typeId);
}
