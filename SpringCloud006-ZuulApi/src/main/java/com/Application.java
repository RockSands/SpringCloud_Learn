package com;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.filter.AccessFilter;

@EnableZuulProxy
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		//http://localhost:1101/${服务名称}/${方法名}
		//http://localhost:1101/eureka-consumer/consumer
		//http://localhost:1101/api-a/dc?accessToken=token
		new SpringApplicationBuilder(Application.class).web(true).run(args);
	}
	
	/**
	 * 注册Filter
	 * @return
	 */
	@Bean
	public AccessFilter accessFilter() {
		return new AccessFilter();
	}
}
