package com.example.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class StudentReport implements Serializable {

    private static final long serialVersionUID = -7235646360995874347L;
    private Integer studentId;
    private Integer reportId;
    private String filepath;
    private String fileurl;
    private String code;
    private Date inTime;
    private String reportType;
    private String url;
}
