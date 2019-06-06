package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -749261754801308721L;
    @TableId(type = IdType.AUTO)
    private int id;
    private String username;
    private String password;
    private Date inTime;
    private Integer roleId;
    private String roleName;
}
