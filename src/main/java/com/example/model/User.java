package com.example.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = -749261754801308721L;
    private int id;
    private String username;
    private String password;
    private Date inTime;
    private Integer roleId;
}
