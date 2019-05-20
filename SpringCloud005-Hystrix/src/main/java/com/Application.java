package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


public class Application {
    public static void main(String[] args) {
    	// http://localhost:2101/consumer
    	SpringApplication.run(Application.class, args);
    }
}
