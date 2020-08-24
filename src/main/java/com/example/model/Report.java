package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Report implements Serializable {
    private static final long serialVersionUID = -9222944861901857327L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer studentId;
    private Integer teacherId;
    private String content;
    private String teacherSuggestion;
    private String teacherName;
    private String studentName;
    private Integer state;
    private Integer type;
    private Integer filesId;
    private Date inTime;
    @TableField(exist = false)
    private String stateName;

    @TableField(exist = false)
    private String typeName;

    @TableField(exist = false)
    private String fileUrl;

    @TableField(exist = false)
    private String fileName;

}

