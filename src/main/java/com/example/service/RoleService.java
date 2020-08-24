package com.example.service;

import com.example.mapper.RoleMapper;
import com.example.mapper.RolePermissionMapper;
import com.example.model.Role;
import com.example.model.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionService rolePermissionService;

    public void insert(String name,Integer[] permissionIds){
        Role role=new Role();
        role.setName(name);
        roleMapper.insert(role);
        insertRolePermission(role,permissionIds);
    }

    public void update(Integer id,String name,Integer[] permissionIds){
        Role role=this.selectById(id);
        role.setName(name);
        roleMapper.updateById(role);
        insertRolePermission(role,permissionIds);
    }

    public Role selectById(Integer roleId) {
        return roleMapper.selectById(roleId);
    }

    public List<Role> selectAll() {
        return roleMapper.selectList(null);
    }

    public void delete(Integer id) {
        // 删除关联关系
        rolePermissionService.deleteByRoleId(id);
        // 删除自己
        roleMapper.deleteById(id);
    }

    public void insertRolePermission(Role role,Integer[] permissionIds){
        for (Integer permissionId:permissionIds) {
            RolePermission rolePermission=new RolePermission();
            rolePermission.setRoleId(role.getId());
            rolePermission.setPermissionId(permissionId);
            rolePermissionService.insert(rolePermission);
        }
    }

    public Integer selectByName(String name){
        return roleMapper.selectByRoleName(name);
    }
}
