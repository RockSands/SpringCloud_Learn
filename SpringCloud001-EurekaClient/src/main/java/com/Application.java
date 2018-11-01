package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 以下为两种@注册服务的区别,起始@EnableEurekaClient中声明了@EnableDiscoveryClient
 * @EnableDiscoveryClient注解是基于spring-cloud-commons依赖，并且在classpath中实现； 
 * @EnableEurekaClient注解是基于spring-cloud-netflix依赖，只能为eureka作用；
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
public class Application {
	// http://localhost:2001/dc
	public static void main(String[] args) {
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
}
