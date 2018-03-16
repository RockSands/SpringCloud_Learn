package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class DcService {
	@Autowired
	RestTemplate restTemplate;

	// 降级效果
	// 经过测试,效果是应该是线程访问,一旦超时即Stop该线程.直接回收
	@HystrixCommand(fallbackMethod = "fallback")
	@GetMapping("/consumer")
	public String dc() {
		try {
			Thread.sleep(500000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return restTemplate.getForObject("http://SERVICE-DC/dc", String.class);
	}

	public String fallback() {
		return "fallback";
	}
}
