package com.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("service-dc")
public interface DcClient {

    @GetMapping("/dc")
    String consumer();

}