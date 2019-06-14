package com.dx.springcloudprovider.controller;

import com.dx.springcloudprovider.pojo.User;
import com.dx.springcloudprovider.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
//    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        Optional<User> one = userRepository.findById(id);
        if (one.isPresent()) {
            User user = one.get();
            user.setInfo("第一台机器");
            return user;
        }else {
            return null;
        }
    }

    @GetMapping("/instance-info")
    public List showInfo() {
        List list = new ArrayList();
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                list.add(instance);
            }
        }
        return list;
    }
}
