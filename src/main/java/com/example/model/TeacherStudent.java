package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class TeacherStudent {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer teacherId;
    private Integer studentId;
}
