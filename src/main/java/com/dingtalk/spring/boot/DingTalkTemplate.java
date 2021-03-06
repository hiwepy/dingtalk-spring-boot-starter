/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.dingtalk.spring.boot;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGettokenResponse;
import com.dingtalk.spring.boot.property.DingTalkCropAppProperties;
import com.dingtalk.spring.boot.property.DingTalkLoginProperties;
import com.dingtalk.spring.boot.property.DingTalkPersonalMiniAppProperties;
import com.dingtalk.spring.boot.property.DingTalkSuiteProperties;
import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.taobao.api.ApiException;

/*
 * https://open-doc.dingtalk.com/microapp/serverapi2/eev437
 * https://blog.csdn.net/yangguosb/article/details/79762565
 * 
 * @author ： <a href="https://github.com/hiwepy">wandl</a>
 */
public class DingTalkTemplate implements InitializingBean {

	private final String DINGTALK_SERVICE = "https://oapi.dingtalk.com";
	private final String METHOD_GET = "GET";
	private Map<String, String> appKeySecret = new ConcurrentHashMap<>();
	private final DingTalkProperties dingtalkProperties;
	
	private final DingTalkAccountOperations accountOps = new DingTalkAccountOperations(this);
	private final DingTalkSnsOperations snsOps = new DingTalkSnsOperations(this);
	private final DingTalkSsoOperations ssoOps = new DingTalkSsoOperations(this);
	private final DingTalkJsapiOperations jsapiOps = new DingTalkJsapiOperations(this);
	
	public DingTalkTemplate(DingTalkProperties dingtalkProperties) {
		this.dingtalkProperties = dingtalkProperties;
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		
		if(!CollectionUtils.isEmpty(this.dingtalkProperties.getCropApps())) {
			for (DingTalkCropAppProperties properties : this.dingtalkProperties.getCropApps()) {
				appKeySecret.put(properties.getAppKey(), properties.getAppSecret());
			}
		}
		if(!CollectionUtils.isEmpty(this.dingtalkProperties.getApps())) {
			for (DingTalkPersonalMiniAppProperties properties : this.dingtalkProperties.getApps()) {
				appKeySecret.put(properties.getAppId(), properties.getAppSecret());
			}
		}
		if(!CollectionUtils.isEmpty(this.dingtalkProperties.getSuites())) {
			for (DingTalkSuiteProperties properties : this.dingtalkProperties.getSuites()) {
				appKeySecret.put(properties.getAppId(), properties.getSuiteSecret());
			}
		}
		if(!CollectionUtils.isEmpty(this.dingtalkProperties.getLogins())) {
			for (DingTalkLoginProperties properties : this.dingtalkProperties.getLogins()) {
				appKeySecret.put(properties.getAppId(), properties.getAppSecret());
			}
		}
		
	}
	
	private final LoadingCache<String, Optional<String>> ACCESS_TOKEN_CACHES = CacheBuilder.newBuilder()
			// 设置并发级别为8，并发级别是指可以同时写缓存的线程数
			.concurrencyLevel(8)
			// 正常情况下access_token有效期为7200秒，有效期内重复获取返回相同结果，并自动续期
			.expireAfterWrite(6000, TimeUnit.SECONDS)
			// 设置缓存容器的初始容量为10
			.initialCapacity(2)
			// 设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
			.maximumSize(10)
			// 设置要统计缓存的命中率
			.recordStats()
			// 设置缓存的移除通知
			.removalListener(new RemovalListener<String, Optional<String>>() {
				@Override
				public void onRemoval(RemovalNotification<String, Optional<String>> notification) {
					System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
				}
			})
			// build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
			.build(new CacheLoader<String, Optional<String>>() {

				@Override
				public Optional<String> load(String keySecret) throws Exception {

					OapiGettokenRequest request = new OapiGettokenRequest();

					JSONObject key = JSONObject.parseObject(keySecret);
					//request.setCorpid(key.getString("corpId"));
					//request.setCorpsecret(key.getString("corpSecret"));
					request.setAppkey(key.getString("appKey"));
					request.setAppsecret(key.getString("appSecret"));
					
					request.setHttpMethod(METHOD_GET);
					
					DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/gettoken");
					OapiGettokenResponse response = client.execute(request);

					if (response.isSuccess()) {
						String token = response.getAccessToken();
						return Optional.fromNullable(token);
					}
					return Optional.fromNullable(null);
				}
			});

	private final LoadingCache<String, Optional<String>> SNS_ACCESS_TOKEN_CACHES = CacheBuilder.newBuilder()
			// 设置并发级别为8，并发级别是指可以同时写缓存的线程数
			.concurrencyLevel(8)
			// 设置写缓存后600秒钟过期
			.expireAfterWrite(6000, TimeUnit.SECONDS)
			// 设置缓存容器的初始容量为10
			.initialCapacity(2)
			// 设置缓存最大容量为100，超过100之后就会按照LRU最近虽少使用算法来移除缓存项
			.maximumSize(10)
			// 设置要统计缓存的命中率
			.recordStats()
			// 设置缓存的移除通知
			.removalListener(new RemovalListener<String, Optional<String>>() {
				@Override
				public void onRemoval(RemovalNotification<String, Optional<String>> notification) {
					System.out.println(notification.getKey() + " was removed, cause is " + notification.getCause());
				}
			})
			// build方法中可以指定CacheLoader，在缓存不存在时通过CacheLoader的实现自动加载缓存
			.build(new CacheLoader<String, Optional<String>>() {

				@Override
				public Optional<String> load(String keySecret) throws Exception {

					OapiSnsGettokenRequest request = new OapiSnsGettokenRequest();

					JSONObject key = JSONObject.parseObject(keySecret);

					request.setAppid(key.getString("appId"));
					request.setAppsecret(key.getString("appSecret"));
					request.setHttpMethod(METHOD_GET);

					DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/sns/gettoken");

					OapiSnsGettokenResponse response = client.execute(request);

					if (response.isSuccess()) {
						String token = response.getAccessToken();
						return Optional.fromNullable(token);
					}
					return Optional.fromNullable(null);

				}
			});

	
	public boolean hasAppKey(String appKey) {
		return appKeySecret.containsKey(appKey);
	} 
	
	public String getAppSecret(String appKey) {
		String appSecret = appKeySecret.get(appKey);
		return appSecret;
	} 
	
	/*
	 * 
	 * 	企业内部开发获取access_token 先从缓存查，再到钉钉查
	 * https://open-doc.dingtalk.com/microapp/serverapi2/eev437
	 * @param appKey    企业Id
	 * @param appSecret 企业应用的凭证密钥
	 * @return the AccessToken
	 * @throws ApiException if get AccessToken Exception
	 */
	public String getAccessToken(String appKey, String appSecret) throws ApiException {
		try {
			
			JSONObject key = new JSONObject();
			key.put("appKey", appKey);
			key.put("appSecret", appSecret);

			Optional<String> opt = ACCESS_TOKEN_CACHES.get(key.toJSONString());
			return opt.isPresent() ? opt.get() : null;
			
		} catch (ExecutionException e) {
			throw new ApiException(e);
		}
	}
	
	/**
	 * 获取钉钉开放应用的ACCESS_TOKEN
	 * 
	 * @param appId    企业Id
	 * @param appSecret 企业应用的凭证密钥
	 * @return the AccessToken
	 * @throws ApiException if get AccessToken Exception
	 */
	public String getSnsAccessToken(String appId, String appSecret) throws ApiException {
		try {
			
			JSONObject key = new JSONObject();
			key.put("appId", appId);
			key.put("appSecret", appSecret);
	
			Optional<String> opt = SNS_ACCESS_TOKEN_CACHES.get(key.toJSONString());
			return opt.isPresent() ? opt.get() : null;
		} catch (ExecutionException e) {
			throw new ApiException(e);
		}
	}

	public DingTalkAccountOperations opsForAccount() {
		return accountOps;
	}

	public DingTalkSnsOperations opsForSns() {
		return snsOps;
	}
	
	public DingTalkSsoOperations opsForSso() {
		return ssoOps;
	}
	
	public DingTalkJsapiOperations opsForJsapi() {
		return jsapiOps;
	}
	
}
