package com.dx.springcloudhystrix.service;


import com.dx.springcloudhystrix.pojo.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RibbonHystrixService {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 使⽤@HystrixCommand注解指定当该⽅法发⽣异常时调⽤的⽅法
     * @param id id
     * @return 通过id查询到的⽤户
     */
    @HystrixCommand(fallbackMethod = "fallback")
    public User findById(Long id) {
        String url = "http://microservice-provider-user/" + id;
        return this.restTemplate.getForObject(url, User.class);
    }

    /**
     * hystrix fallback⽅法
     * @param id id
     * @return 默认的⽤户
     */
    public User fallback(Long id){
        log.error("异常发⽣，进⼊fallback⽅法，接收的参数：id = {}" , id);
        User user = new User();
        user.setId(-1L);
        user.setUsername("default username");
        user.setAge(0);
        user.setInfo("有服务器宕机，无法获取到数据");
        return user;
    }
}
