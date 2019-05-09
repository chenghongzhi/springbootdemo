package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.PermissionMapper;
import com.example.model.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionService rolePermissionService;

    public List<Permission> selectByRoleId(Integer roleId){
        return permissionMapper.selectByRoleId(roleId);
    }

    // 根据父节点查询子节点
    public List<Permission> selectByPid(Integer pid) {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(Permission::getPid, pid);
        return permissionMapper.selectList(wrapper);
    }

    public void deleteByPermissionId(Integer id){
        Permission permission=permissionMapper.selectById(id);
        // 如果删除的是父节点，那么删除父节点以下的所有子节点
        if (permission.getPid()==0) {
            List<Permission> permissionList=this.selectByPid(permission.getId());
            permissionList.forEach(permission1 -> {
                rolePermissionService.deleteByPermissionId(permission1.getId());
                permissionMapper.deleteById(permission1.getId());
            });
        }else {
            rolePermissionService.deleteByPermissionId(id);
        }
        permissionMapper.deleteById(id);
    }

    public Permission selectByPermissionId(Integer id){
        return permissionMapper.selectById(id);
    }

    public void insert(Permission permission) {
        permissionMapper.insert(permission);
    }

    public void update(Permission permission) {
        permissionMapper.updateById(permission);
    }

    public Map<String,List<Permission>> selectAll(){
        Map<String,List<Permission>> map=new LinkedHashMap<>();
        List<Permission> permissions=this.selectByPid(0);
        permissions.forEach(permission -> map.put(permission.getName(),this.selectByPid(permission.getId())));
        return map;
    }

}
