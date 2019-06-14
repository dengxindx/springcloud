package com.dx.springcloudfeignhystrix.service;

import com.dx.springcloudfeignhystrix.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 使⽤@FeignClient("microservice-provider-user")注解绑定micros
 * ervice-provider-user服务，还可以使⽤url参数指定⼀个URL。
 */
@FeignClient(name = "microservice-provider-user", fallback = UserFeignHystrixClient.HystrixClientFallback.class)
@Service
public interface UserFeignHystrixClient {

    @RequestMapping("/{id}")
    User findById(@RequestParam("id") Long id);

    @Component
    @Slf4j
    class HystrixClientFallback implements UserFeignHystrixClient {

        @Override
        public User findById(Long id) {
            log.error("异常发⽣，进⼊fallback⽅法，接收的参数：id = {}", id);
            User user = new User();
            user.setId(-1L);
            user.setUsername("default username");
            user.setAge(0);
            user.setInfo("有服务器宕机，返回默认数据");
            return user;
        }
    }
}