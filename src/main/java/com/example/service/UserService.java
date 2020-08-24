package com.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.mapper.UserMapper;
import com.example.model.MyPage;
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
        user.setState(0);
        userMapper.insert(user);
    }

    public void update(User user){
        ByteSource credentialsSalt = ByteSource.Util.bytes(user.getUsername());
        String password = new SimpleHash("MD5", user.getPassword(), credentialsSalt, 1024).toHex();
        user.setPassword(password);
        userMapper.updateById(user);
    }

    public void updateUser(User user){
        userMapper.updateById(user);
    }

    public User selectByName(String name){
        return userMapper.selectByName(name);
    }

    public MyPage<User> selectAllByPage(Integer pageNo){
        MyPage<User> myPage = new MyPage<>(pageNo, 10);
        return userMapper.selectAll(myPage);
    }

    public MyPage<User> selectAllStudentByPage(Integer pageNo){
        MyPage<User> myPage = new MyPage<>(pageNo, 10);
        myPage.setSelectInt(2);
        return userMapper.selectAllByRoleId(myPage);
    }

    public MyPage<User> selectAllTeacherByPage(Integer pageNo){
        MyPage<User> myPage = new MyPage<>(pageNo, 10);
        myPage.setSelectInt(1);
        return userMapper.selectAllByRoleId(myPage);
    }

    public MyPage<User> selectAllGroupLeaderByPage(Integer pageNo){
        MyPage<User> myPage = new MyPage<>(pageNo, 10);
        myPage.setSelectInt(3);
        return userMapper.selectAllByRoleId(myPage);
    }

    public void delete(Integer id){
        userMapper.deleteById(id);
    }

    public List<User> selectStudent(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getRoleId,2).eq(User::getState,0);
        return userMapper.selectList(wrapper);
    }

    public List<User> selectTeacher(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(User::getRoleId,1).eq(User::getState,0);
        return userMapper.selectList(wrapper);
    }


    public boolean accountAvailable(String name){
        User user = this.selectByName(name);
        if (user ==null) {
            return true;
        }
        return false;
    }

    public void updateState(Integer state,Integer id){
        userMapper.updateState(state,id);
    }


}
