package com.dx.springcloudhystrixdashboard.config;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * springboot2.x需要添加如下配置
 * 可通过调用http://localhost:8030/hystrix.stream查看实时监控,
 * 调用http://localhost:8030/hystrix查看可视化视图监控
 * 需要调用项目内的任意接口才有data，不然只会出现ping
 */
@Configuration
public class ServletRegistrationBeanConfig {

    // 解决hystrix.stream无法访问问题
    @Bean
    public ServletRegistrationBean getServlet() {
        HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(hystrixMetricsStreamServlet);
        servletRegistrationBean.setLoadOnStartup(1);
        servletRegistrationBean.addUrlMappings("/actuator/hystrix.stream");
        servletRegistrationBean.setName("HystrixMetricsStreamServlet");
        return servletRegistrationBean;
    }
}
