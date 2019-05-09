package com.example.model;

import java.io.Serializable;

public class RolePermission  implements Serializable {
    private static final long serialVersionUID = -3119632123750197647L;
    private Integer roleId;
    private Integer permissionId;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(Integer permissionId) {
        this.permissionId = permissionId;
    }
}
