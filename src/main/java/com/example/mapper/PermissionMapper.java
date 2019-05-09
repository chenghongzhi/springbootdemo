package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.Permission;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> selectByRoleId(Integer roleId);
}
