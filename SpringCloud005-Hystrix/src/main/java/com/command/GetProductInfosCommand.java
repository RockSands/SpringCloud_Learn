package com.command;

import com.alibaba.fastjson.JSONObject;
import com.model.ProductInfo;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import com.utils.HttpClientUtils;

import rx.Observable;
import rx.Subscriber;
import rx.schedulers.Schedulers;

/**
 * 批量查询多个商品数据的command
 * 
 * 
command可以继承一个HystrixCommand或一个HystrixObservableCommand对象

HystrixCommand主要用于仅仅会返回一个结果的调用
HystrixObservableCommand主要用于可能会返回多条结果的调用

HystrixCommand command = new HystrixCommand(arg1, arg2);
HystrixObservableCommand command = new HystrixObservableCommand(arg1, arg2);

2、调用command的执行方法

执行Command就可以发起一次对依赖服务的调用
要执行Command，需要在4个方法中选择其中的一个：execute()，queue()，observe()，toObservable()

其中execute()和queue()仅仅对HystrixCommand适用

execute()：调用后直接block住，属于同步调用，直到依赖服务返回单条结果，或者抛出异常
queue()：返回一个Future，属于异步调用，后面可以通过Future获取单条结果
observe()：订阅一个Observable对象，Observable代表的是依赖服务返回的结果，获取到一个那个代表结果的Observable对象的拷贝对象
toObservable()：返回一个Observable对象，如果我们订阅这个对象，就会执行command并且获取返回结果

K             value   = command.execute();
Future<K>     fValue  = command.queue();
Observable<K> ohValue = command.observe();         
Observable<K> ocValue = command.toObservable();    

execute()实际上会调用queue().get().queue()，接着会调用toObservable().toBlocking().toFuture()

 * @author Administrator
 *
 */
public class GetProductInfosCommand extends HystrixObservableCommand<ProductInfo> {

	private String[] productIds;
	
	public GetProductInfosCommand(String[] productIds) {
		super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
		this.productIds = productIds;
	}
	
	@Override
	protected Observable<ProductInfo> construct() {
		return Observable.create(new Observable.OnSubscribe<ProductInfo>() {

			public void call(Subscriber<? super ProductInfo> observer) {
				try {
					for(String productId : productIds) {
						String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
						String response = HttpClientUtils.sendGetRequest(url);
						ProductInfo productInfo = JSONObject.parseObject(response, ProductInfo.class); 
						observer.onNext(productInfo); 
					}
					observer.onCompleted();
				} catch (Exception e) {
					observer.onError(e);  
				}
			}
			
		}).subscribeOn(Schedulers.io());
	}

}
