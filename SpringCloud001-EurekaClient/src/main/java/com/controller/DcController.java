package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自定义,返回所有注册的服务
 *
 */
@RestController
public class DcController {

	@Autowired
	DiscoveryClient discoveryClient;

	@Value("${server.port}")
	String port;

	@GetMapping("/dc")
	public String dc() {
		String services = "Services-Port[" + port + "]: " + discoveryClient.getServices();
		System.out.println(services);
		return services;
	}

}
