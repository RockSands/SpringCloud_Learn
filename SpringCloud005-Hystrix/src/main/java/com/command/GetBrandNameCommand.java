package com.command;

import com.cache.BrandCache;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * 获取品牌名称的command
 * 
 * hystrix进行资源隔离，其实是提供了一个抽象，叫做command
 * 
 * command group
   是一个非常重要的概念，默认情况下，因为就是通过command group来定义一个线程池的，
   而且还会通过command group来聚合一些监控和报警信息.
   同一个command group中的请求，都会进入同一个线程池中
 * @author Administrator
 *
 */
public class GetBrandNameCommand extends HystrixCommand<String> {
	
	private Long brandId;
	
	public GetBrandNameCommand(Long brandId) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("BrandInfoService"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetBrandNameCommand"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetBrandInfoPool"))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
						.withCoreSize(15) // 核心线程15个
						.withQueueSizeRejectionThreshold(10)) // 如果多过10个就Rejection
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withFallbackIsolationSemaphoreMaxConcurrentRequests(15))
				);  
		this.brandId = brandId;
	}
	
	@Override
	protected String run() throws Exception {
		// 调用一个品牌服务的接口
		// 如果调用失败了，报错了，那么就会去调用fallback降级机制
		throw new Exception();
	}
	
	@Override
	protected String getFallback() {
		System.out.println("从本地缓存获取过期的品牌数据，brandId=" + brandId);  
		return BrandCache.getBrandName(brandId);
	}

}
