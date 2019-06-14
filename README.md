# springcloud个人笔记

## 目录

- 1[eureka-serve服务注册中心](#related-springcloud-eureka-serve)
- 2[eureka-client服务提供方](#related-springcloud-eureka-client)
- 3[consul](#related-consul)
- 4[eureka-provider使用eureka服务注册中心](#related-eureka-provider)
- 5[ribbon负载均衡](#related-ribbon)

<a name="related-springcloud-eureka-serve"></a>
### 1、eureka-serve服务注册中心

启动类上添加注解<code>@EnableEurekaServer</code>开启服务注册中心

在默认情况会将自己作为客户端注册，可以在application.properties中禁用：
```properties
spring.application.name=springcloud-eureka-server
server.port=1001

eureka.instance.hostname=localhost
#是否将自己注册到Eureka Server上，默认为true
eureka.client.register-with-eureka=false
#是否从Eureka Server上获取注册信息，默认为true
eureka.client.fetch-registry=false
```

版本号问题产生的异常：NoSuchMethodError，更新springboot版本或者cloud版本

```text
<dependencies>
    <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-starter-eureka-server -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka-server</artifactId>
        <version>1.4.7.RELEASE</version>
    </dependency>
</dependencies>
   
<!-- Spring Cloud dependencies -->
<dependencyManagement>
    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.springframework.cloud/spring-cloud-dependencies -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Greenwich.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

<a name="related-springcloud-eureka-client"></a>
### 2、eureka-client服务提供方

引入依赖：
```text
<properties>
    <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
</properties>
 
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
</dependencies>
 
<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```

启动类激活Eureka中的DiscoveryClient实现：<code>@EnableDiscoveryClient</code>

配置：
```properties
#
spring.application.name=springcloud-eureka-client
server.port=2001
#注册到对应的服务中心上
eureka.client.serviceUrl.defaultZone=http://localhost:1001/eureka/
```

<a name="related-consul"></a>
### 3、consul

下载安装包：
 
consul.exe agent -dev 本地模式，将会使用127.0.0.1 的ip地址
 
consul.exe agent -dev -client 192.168.xx.xx  
 
常用命令：
- agent：运行一个consul agent 示例：consul agent -dev
- join：将agent添加到consul集群 示例：consul join IP
- members：列出consul cluster集群中的members 示例：consul members
- leave：将节点移除所在集群 示例：consul leave



<a name="related-eureka-provider"></a>
### 4、eureka-provider使用eureka服务注册中心

启动类开启:<code>@EnableDiscoveryClient</code>

使用spring-data-jpa操作H2数据库，H2是一个开源的嵌入式数据库引擎，采用java语言编写，不受平台的限制，同时H2提供了一个十分方便的web控制台用于操作和管理数据库内容
 
添加pom依赖：
```text
<dependencies>
    <!-- 添加Eureka的依赖 -->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-eureka</artifactId>
    </dependency>
  
    <!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
  
    <!-- https://mvnrepository.com/artifact/com.h2database/h2 -->
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
    </dependency>
</dependencies>
```

配置文件：
```properties
server.port=8000
  
# 项⽬名称尽量⽤⼩写
spring.application.name=microservice-provider-user
#spring.jpa.generate-ddl=false
#spring.jpa.show-sql=true
# 标准的Hibernate属性值有 none ， validate ， update ， create ， create-drop
spring.jpa.hibernate.ddl-auto=none
# 指定数据源类型，这里使用h2内嵌数据库测试
#spring.datasource.platform=h2
# 指定h2数据库的建表脚本
spring.datasource.schema=classpath:schema.sql
# 指定h2数据库的insert脚本
spring.datasource.data=classpath:data.sql
  
spring.datasource.url=jdbc:h2:D:/dengxin/aaaJavaStudy/h2database/data/dx2
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=dx
spring.datasource.password=123
#spring.h2.console.enabled=true
  
#logging.level.root=INFO
#logging.level.org.hibernate=INFO
#logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE
#logging.level.org.hibernate.type.descriptor.sql.BasicExtractor=TRACE
#logging.level.com.itmuch.youran.persistence=TRACE
  
# 指定注册中心地址
eureka.client.serviceUrl.defaultZone=http://discovery:8761/eureka/
# 默认false不使用主机名来定义注册中心的地址，而使用IP地址的形式，如果设置了eureka.instance.ip-address 属性，则使用该属性配置的IP，否则自动获取除环路IP外的第一个IP地址
eureka.instance.preferIpAddress=true
```

先启动eureka-server服务注册中心，再启动provider注册到注册中心上去

内容详见项目：springcloud-provider

<a name="related-ribbon"></a>
### 5、ribbon负载均衡

启动类开启服务发现：<code>@EnableDiscoveryClient</code>
```java
package com.dx.springcloudribbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringcloudRibbonApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudRibbonApplication.class, args);
	}

	/**
	 * 实例化RestTemplate，通过@LoadBalanced注解开启负载均衡能力
	 * @return
	 */
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
```

配置文件：
```properties
server.port=8010

spring.application.name=microservice-consumer-movie-ribbon
# 指定注册中心地址
eureka.client.service-url.defaultZone=http://discovery:8761/eureka/
eureka.instance.prefer-ip-address=true
# 是否注册到服务中心上
#eureka.client.register-with-eureka=false
```

负载调用：
```java
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

```

***注意：如果服务中心有机器宕机，进行负载均衡的时候依旧会调用宕机的机器，这种情况需要处理***

