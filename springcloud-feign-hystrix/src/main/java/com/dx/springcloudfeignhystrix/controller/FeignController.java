package com.dx.springcloudfeignhystrix.controller;

import com.dx.springcloudfeignhystrix.pojo.User;
import com.dx.springcloudfeignhystrix.service.UserFeignHystrixClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {

    @Autowired
    private UserFeignHystrixClient userFeignHystrixClient;

    @GetMapping("/feign/{id}")
    public User findById(@PathVariable Long id){
        return userFeignHystrixClient.findById(id);
    }
}
