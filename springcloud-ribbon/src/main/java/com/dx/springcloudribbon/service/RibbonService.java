package com.dx.springcloudribbon.service;

import com.dx.springcloudribbon.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RibbonService {

    @Autowired
    private RestTemplate restTemplate;

    public User findById(Long id){
        String url = "http://microservice-provider-user/" + id;
        return restTemplate.getForObject(url, User.class);
    }
}
