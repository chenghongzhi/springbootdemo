package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Report implements Serializable {
    private static final long serialVersionUID = -9222944861901857327L;
    @TableId(type = IdType.AUTO)
    private int id;
    private Integer studentId;
    private String content;
    private String remark;
    private String teacherName;
    private String studentName;
    private String status;
    private Date inTime;
}

