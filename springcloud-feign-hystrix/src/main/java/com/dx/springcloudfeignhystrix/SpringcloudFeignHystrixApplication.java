package com.dx.springcloudfeignhystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker    // 开启断路器功能
@EnableFeignClients
public class SpringcloudFeignHystrixApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudFeignHystrixApplication.class, args);
	}

}
