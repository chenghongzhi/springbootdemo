package com.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class State {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String name;

}
