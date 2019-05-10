package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mapper.UserMapper;
import com.example.model.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User selectById(Integer id){
        return userMapper.selectById(id);
    }

    public void insert(User user){
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
        String password = new SimpleHash("MD5", user.getPassword(), credentialsSalt, 1024).toHex();
        user.setPassword(password);
        user.setInTime(new Date());
        userMapper.insert(user);
    }

    public void update(User user){
            userMapper.updateById(user);

    }
    public void removeById(Integer id){
        if (userMapper.selectById(id)!=null) {
            userMapper.deleteById(id);
        }
    }

    public User selectByName(String name){
        return userMapper.selectByName(name);
    }

    public IPage<User> selectAllByPage(Integer pageNo){
        Page<User> page=new Page<>(pageNo,5);
        return userMapper.selectPage(page,null);
    }

    public void delete(Integer id){
        userMapper.deleteById(id);
    }
}
