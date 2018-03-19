package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @EnableEurekaServer 启动Eureka服务注册服务
 */
@EnableEurekaServer
@SpringBootApplication
public class Application {
	// 启动后访问Eureka服务页面，http://localhost:1001/
    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                    .web(true).run(args);
    }
}