package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.model.MyPage;
import com.example.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper extends BaseMapper<User> {

    User selectByName(String username);

    MyPage<User> selectAll(@Param("pg") MyPage<User> myPage);

    MyPage<User> selectAllByRoleId(@Param("pg") MyPage<User> myPage);

    void updateState(Integer state,Integer id);
}
