package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.RolePermissionMapper;
import com.example.model.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RolePermissionService {
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    /**
     * 根据角色id删除关联关系
     * @param roleId
     */
    public void deleteByRoleId(Integer roleId) {
        QueryWrapper<RolePermission> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(RolePermission::getRoleId,roleId);
        rolePermissionMapper.delete(wrapper);
    }

    /***
     * 根据权限id删除关联关系
     * @param permissionId
     */
    public void deleteByPermissionId(Integer permissionId) {
        QueryWrapper<RolePermission> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(RolePermission::getPermissionId,permissionId);
        rolePermissionMapper.delete(wrapper);
    }

    public void insert(RolePermission rolePermission){
        rolePermissionMapper.insert(rolePermission);
    }

    public List<RolePermission> selectByRoleId(Integer id){
        QueryWrapper<RolePermission> wrapper=new QueryWrapper<>();
        wrapper.lambda().eq(RolePermission::getPermissionId,id);
        return rolePermissionMapper.selectList(wrapper);
    }
}
