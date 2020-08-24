package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Date;

@Data
@Component
public class Records {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer userId;
    private Integer reportId;
    private Integer stateId;
    private Integer typeId;
    private String suggestion;
    private Date time;
}
