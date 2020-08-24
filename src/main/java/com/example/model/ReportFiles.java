package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ReportFiles {
    @TableId(type = IdType.AUTO)
    private int id;
    private Integer reportId;
    private String fileName;
    private String fileUrl;
    private String filePath;
    private String fileCode;
    private Date inTime;
    private String submitterName;
}
