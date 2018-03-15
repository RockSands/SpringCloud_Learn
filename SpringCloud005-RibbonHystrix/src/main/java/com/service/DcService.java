package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

/**
 * @author Administrator
 *
 */
@Service
public class DcService {
    @Autowired
    RestTemplate restTemplate;

    // 降级效果
    @HystrixCommand(fallbackMethod = "myFallback")
    @GetMapping("/consumer")
    public String dc() {
	return restTemplate.getForObject("http://SERVICE-DC/dc", String.class);
    }

    public String fallback() {
	return "fallback";
    }
}
