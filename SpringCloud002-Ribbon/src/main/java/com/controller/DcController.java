package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class DcController {
	@Autowired
	RestTemplate restTemplate;

	@GetMapping("/consumer")
	public String dc() {
		// EUREKA-CLIENT 服务名称
		return restTemplate.getForObject("http://EUREKA-CLIENT/dc", String.class);
	}
}
