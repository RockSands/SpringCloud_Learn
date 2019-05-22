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
 * hystrix进行资源隔离，其实是提供了一个抽象，叫做command,即一个command代表一个对外的接口.
 * 
 * Command group
 * 即withGroupKey, command一般代表一个接口能力,而Group为逻辑组.
 * 一个CommandGroup默认使用一个线程池（支持手动配置一个线程池，通过ThreadPoolKey），
 * Hystrix会按照ThreadPool进行统计、监控、报警。
 * 
 * 
 * HystrixThreadPool
 * 即ThreadPoolKey, 一个现场池Hystrix会对其进行统计、监控、报警。也会对不同的线程池进行隔离
 * 
 * 
 * coreSize
 * 即withCoreSize，设置现场池的核心线程数量，系统保证这些线程的存活。
 * 
 * QueueSize
 * 即withMaxQueueSize，即等待队列。表示超过核心进程会进入队列。
 * 
 * QueueSizeRejectionThreshold
 * withQueueSizeRejectionThreshold，设置请求上限，如果请求超过上限hystrix会直接拒绝掉
 * 
 * allowMaximumSizeToDivergeFromCoreSize
 * 是否开启线程池自动动态调整策略，如果开启，下方面的参数生效！
 * 
 * maximumSize
 * 设置线程池的最大大小，只有在开启allowMaximumSizeToDivergeFromCoreSize的时候才能生效
 * 
 * keepAliveTimeMinutes
 * 设置非核心线程的存活时间，需要allowMaximumSizeToDivergeFromCoreSize开启。
 * 
 * 
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
