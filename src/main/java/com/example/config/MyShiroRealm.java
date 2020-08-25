package com.example.config;

import com.example.model.Permission;
import com.example.model.Role;
import com.example.model.User;
import com.example.service.PermissionService;
import com.example.service.RoleService;
import com.example.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.List;
import java.util.stream.Collectors;

public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private UserService userService;
    @Autowired
    @Lazy
    private RoleService roleService;
    @Autowired
    @Lazy
    private PermissionService permissionService;
    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo=new SimpleAuthorizationInfo();
        User user=userService.selectByName(principalCollection.toString());
        // 获得用户的角色，及权限进行绑定
        Role role=roleService.selectById(user.getRoleId());
        authorizationInfo.addRole(role.getName());
        List<Permission> permissions=permissionService.selectByRoleId(user.getRoleId());
        List<String> permissionValues = permissions.stream().map(Permission::getValue).collect(Collectors.toList());
        // 将权限的String集合添加进AuthorizationInfo里，后面请求鉴权有用
        authorizationInfo.addStringPermissions(permissionValues);
        return authorizationInfo;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String) authenticationToken.getPrincipal();
        User user=userService.selectByName(username);
        if (user==null) {
            throw new AuthenticationException("账号不存在，请重试");
        }
        String realmName=getName();
        ByteSource salt=ByteSource.Util.bytes(user.getUsername());
        AuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(username,user.getPassword()
                ,salt,realmName);
        return authenticationInfo;
    }
}
