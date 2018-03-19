package com;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@EnableHystrix
@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableDiscoveryClient
@SpringCloudApplication
public class Application {
	public static void main(String[] args) {
		// http://localhost:1301/hystrix
		SpringApplication.run(Application.class, args);
	}
}
