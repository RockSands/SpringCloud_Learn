package com.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * eureka-client标识eureka的一个服务
 * Feign默认开启Robbin
 * fallback: Hystrix的配置
 */
@FeignClient(name = "eureka-client", fallback = DcClientFallback.class)
public interface DcClient {

    @GetMapping("/dc")
    String consumer();

}