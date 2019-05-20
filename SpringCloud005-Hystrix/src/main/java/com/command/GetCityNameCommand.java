package com.command;

import com.cache.LocationCache;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolKey;

/**
 * 获取城市名称的command
 * @author Administrator
 * 
 * 使用信号量，semaphore方式构建Command
 * 信号量：适合，你的访问不是对外部依赖的访问，而是对内部的一些比较复杂的业务逻辑的访问，
 * 但是像这种访问，系统内部的代码，其实不涉及任何的网络请求，那么只要做信号量的普通限流就可以了，
 * 因为不需要去捕获timeout类似的问题，
 * 
 * 简而言之, 信号量适合做小型的内部快速接口,不适合外部调用, 使用有限制.  但由于信号量直接和Tomcat线程对接,
 * 而不使用Hystrix的内部线程池,因而开销小,但是功能有限
 * 
 * 参数execution.isolation.strategy 指定了是信号量还是线程池
 *
 */
public class GetCityNameCommand extends HystrixCommand<String> {

	private Long cityId;
	
	public GetCityNameCommand(Long cityId) {
		super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetCityNameGroup"))
				.andCommandKey(HystrixCommandKey.Factory.asKey("GetCityNameCommand"))
				.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("GetCityNamePool"))
		        .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
		        		.withExecutionIsolationStrategy(ExecutionIsolationStrategy.SEMAPHORE)
		        		.withExecutionIsolationSemaphoreMaxConcurrentRequests(15)));
		this.cityId = cityId;
	}
	
	@Override
	protected String run() throws Exception {
		return LocationCache.getCityName(cityId);
	}
	
}
