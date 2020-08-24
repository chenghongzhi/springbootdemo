package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.GroupTeacherMapper;
import com.example.model.LeaderTeacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GroupTeacherService {
    @Autowired
    private GroupTeacherMapper groupTeacherMapper;

    public void insert(LeaderTeacher leaderTeacher){
        groupTeacherMapper.insert(leaderTeacher);
    }

    public LeaderTeacher selectByTeacherId(Integer teacherId){
        QueryWrapper<LeaderTeacher> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(LeaderTeacher::getTeacherId,teacherId);
        return groupTeacherMapper.selectOne(wrapper);
    }

    public List<LeaderTeacher> selectBygroupLeaderId(Integer groupLeaderId){
        QueryWrapper<LeaderTeacher> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(LeaderTeacher::getLeaderId,groupLeaderId);
        return groupTeacherMapper.selectList(wrapper);
    }

    public void deleteBygroupLeaderId(Integer groupLeaderId){
        QueryWrapper<LeaderTeacher> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq( LeaderTeacher::getLeaderId, groupLeaderId);
        groupTeacherMapper.delete(wrapper);
    }

    public void deleteByTeacherrId(Integer teacherId){
        QueryWrapper<LeaderTeacher> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq( LeaderTeacher::getTeacherId, teacherId);
        groupTeacherMapper.delete(wrapper);
    }

}
