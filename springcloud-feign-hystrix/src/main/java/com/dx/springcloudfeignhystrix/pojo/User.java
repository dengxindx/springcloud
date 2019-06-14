package com.dx.springcloudfeignhystrix.pojo;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String username;

    private Integer age;

    private String info;
}