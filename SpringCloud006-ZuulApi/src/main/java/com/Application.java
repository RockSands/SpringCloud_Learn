package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		//http://localhost:1101/${服务名称}/${方法名}
		//http://localhost:1101/eureka-consumer/consumer
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
}
