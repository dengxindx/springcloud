package com.dx.springcloudfeign.controller;

import com.dx.springcloudfeign.pojo.User;
import com.dx.springcloudfeign.service.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private UserFeignClient userFeignClient;

    @GetMapping("feign/{id}")
    public User findByIdFeign(@PathVariable Long id) {
        User user = userFeignClient.findByIdFeign(id);
        return user;
    }
}
