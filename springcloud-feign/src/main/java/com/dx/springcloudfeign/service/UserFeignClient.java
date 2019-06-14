package com.dx.springcloudfeign.service;

import com.dx.springcloudfeign.pojo.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使⽤@FeignClient("microservice-provider-user")注解绑定micros
 * ervice-provider-user服务，还可以使⽤url参数指定⼀个URL。
 */
@FeignClient(name = "microservice-provider-user")
@Service
public interface UserFeignClient {

    @RequestMapping("/{id}")
    public User findByIdFeign(@RequestParam("id") Long id);
}
