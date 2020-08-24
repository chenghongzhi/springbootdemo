package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.RecordsMapper;
import com.example.model.Records;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RecordsService {
    @Autowired
    private RecordsMapper recordsMapper;

    public void insert(Records records){
        recordsMapper.insert(records);
    }

    public List<Records> selectAllRecordsByReportId(Integer userId,Integer typeId){
        return recordsMapper.selectAllRecordsByReportId(userId,typeId);
    }




}
