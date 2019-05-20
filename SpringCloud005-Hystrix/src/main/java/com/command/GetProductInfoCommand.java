package com.command;

import com.alibaba.fastjson.JSONObject;
import com.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.utils.HttpClientUtils;

/**
 * 获取商品信息
 * 
 * getCacheKey()  如果实现就表示打开缓存实现,需要HystrixRequestContext.initializeContext();
 * 把context打开,context实现缓存
 * @author Administrator
 *
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfo> {

	public static final HystrixCommandKey KEY = HystrixCommandKey.Factory.asKey("GetProductInfoCommand");
	
	private Long productId;
	
	public GetProductInfoCommand(Long productId) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ProductInfoService"))
				.andCommandKey(KEY)
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetProductInfoPool"))
				.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
						.withCoreSize(10) // 运行的线程数
						.withMaxQueueSize(12) // 等待的队列
						.withQueueSizeRejectionThreshold(15)) //如果超过15个就开始Rejection
				.andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
						.withCircuitBreakerRequestVolumeThreshold(30)
						.withCircuitBreakerErrorThresholdPercentage(40)
						.withCircuitBreakerSleepWindowInMilliseconds(3000)
						.withExecutionTimeoutInMilliseconds(500)
						.withFallbackIsolationSemaphoreMaxConcurrentRequests(30))  
				);  
		this.productId = productId;
	}
	
	@Override
	protected ProductInfo run() throws Exception {
		System.out.println("调用接口，查询商品数据，productId=" + productId); 
		
		if(productId.equals(-1L)) {
			throw new Exception();
		}
		
		if(productId.equals(-2L)) {
			Thread.sleep(3000);  
		}
		
		if(productId.equals(-3L)) {
//			Thread.sleep(250); 
		}
		// 请求外部
		String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
		String response = HttpClientUtils.sendGetRequest(url);
		return JSONObject.parseObject(response, ProductInfo.class);  
	}
	
	@Override
	protected String getCacheKey() {
		return "product_info_" + productId;
	}
	
	/* 
	 * 降级处理
	 */
	@Override
	protected ProductInfo getFallback() {
		ProductInfo productInfo = new ProductInfo();
		productInfo.setName("降级商品");  
		return productInfo;
	}
	
	/**
	 * 
	 * 删除缓存
	 * @param productId
	 */
	public static void flushCache(Long productId) {
		HystrixRequestCache.getInstance(KEY,
                HystrixConcurrencyStrategyDefault.getInstance()).clear("product_info_" + productId);
	}
	
}
