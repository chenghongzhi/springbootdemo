package com.example.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Report implements Serializable {
    private static final long serialVersionUID = -9222944861901857327L;
    private int id;
    private String fileurl;
    private String filepath;
    private Date inTime;
    private String content;
    private String remark;
    private String teacherName;
    private String studentName;
    private String code;
    private String status;
}

