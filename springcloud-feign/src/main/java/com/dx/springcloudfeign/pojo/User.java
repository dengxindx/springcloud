package com.dx.springcloudfeign.pojo;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String username;

    private Integer age;

    private String info;
}