package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.TypeMapper;
import com.example.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TypeService {
    @Autowired
    private TypeMapper typeMapper;

    public List<Type> selectAll(){
        return typeMapper.selectList(null);
    }
}
