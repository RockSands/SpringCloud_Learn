package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.DcService;

@RestController
public class DcController {
	@Autowired
	DcService dcService;

	@GetMapping("/consumer")
	public String dc() {
		return dcService.dc();
	}
}
