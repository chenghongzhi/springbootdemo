package com.example.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 4690631390950764690L;
    private int id;
    private String name;
}
