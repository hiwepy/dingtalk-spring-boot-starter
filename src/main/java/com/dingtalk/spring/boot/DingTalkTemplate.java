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
import com.dingtalk.api.request.OapiDepartmentGetRequest;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetPersistentCodeRequest;
import com.dingtalk.api.request.OapiSnsGetSnsTokenRequest;
import com.dingtalk.api.request.OapiSnsGettokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetUseridByUnionidRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiDepartmentGetResponse;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetPersistentCodeResponse;
import com.dingtalk.api.response.OapiSnsGetSnsTokenResponse;
import com.dingtalk.api.response.OapiSnsGettokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetUseridByUnionidResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.spring.boot.bean.JsapiTicketSignature;
import com.dingtalk.spring.boot.property.DingTalkCropAppProperties;
import com.dingtalk.spring.boot.property.DingTalkLoginProperties;
import com.dingtalk.spring.boot.property.DingTalkPersonalMiniAppProperties;
import com.dingtalk.spring.boot.property.DingTalkSuiteProperties;
import com.dingtalk.spring.boot.utils.DingTalkUtils;
import com.dingtalk.spring.boot.utils.RandomUtils;
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
	 * @return
	 * @throws ExecutionException
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
	
	/*
	 * 获取钉钉开放应用的ACCESS_TOKEN
	 * 
	 * @param appKey
	 * @param appSecret
	 * @return
	 * @throws ExecutionException
	 */
	public String getOpenToken(String appId, String appSecret) throws ApiException {
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
	
	/*
	 * 企业内部应用免登录：通过免登授权码和access_token获取用户信息
	 * https://ding-doc.dingtalk.com/doc#/serverapi2/clotub
	 * @throws ApiException 
	 */
	public OapiUserGetuserinfoResponse getUserinfoBycode( String code, String accessToken) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/user/getuserinfo");
		OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
		request.setCode(code);
		request.setHttpMethod(METHOD_GET);
		return client.execute(request, accessToken);
	}
	
	/*
	 * 第三方应用钉钉扫码登录：通过临时授权码Code获取用户信息，临时授权码只能使用一次。
	 * https://open-doc.dingtalk.com/microapp/serverapi2/kymkv6
	 * @throws ApiException 
	 */
	public OapiSnsGetuserinfoBycodeResponse getSnsGetuserinfoBycode( String code, String accessKey, String accessSecret) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/sns/getuserinfo_bycode");
		OapiSnsGetuserinfoBycodeRequest request = new OapiSnsGetuserinfoBycodeRequest();
		request.setTmpAuthCode(code);
		return client.execute(request, accessKey, accessSecret);
	}
	
	/*
	 * 根据unionid获取userid
	 * https://open-doc.dingtalk.com/microapp/serverapi2/ege851#-5
	 * @throws ApiException 
	 */
	public OapiUserGetUseridByUnionidResponse getUseridByUnionid( String unionid, String accessToken) throws ApiException {
		
		DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/user/getUseridByUnionid");
		OapiUserGetUseridByUnionidRequest request = new OapiUserGetUseridByUnionidRequest();
		request.setUnionid(unionid);
		request.setHttpMethod(METHOD_GET);
		
		return client.execute(request, accessToken);
	}
	
	/*
	 * 获取用户授权的持久授权码
	 * 
	 * @param accessToken
	 * @return
	 */
	public String getPersistentCode(String accessToken, String code) throws ApiException  {
		DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/sns/get_persistent_code");
		OapiSnsGetPersistentCodeRequest request = new OapiSnsGetPersistentCodeRequest();
		request.setTmpAuthCode(code);
		OapiSnsGetPersistentCodeResponse response = client.execute(request, accessToken);
		return response.getBody();
	}

	/*
	 * 获取用户授权的SNS_TOKEN
	 * 
	 * @param openId
	 * @param persistentCode
	 * @param accessToken    开放应用的token
	 * @return
	 */
	public String getSnsToken(String openId, String persistentCode, String accessToken) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/sns/get_sns_token");
		OapiSnsGetSnsTokenRequest request = new OapiSnsGetSnsTokenRequest();
		request.setOpenid(openId);
		request.setPersistentCode(persistentCode);
		OapiSnsGetSnsTokenResponse response = client.execute(request, accessToken);
		return response.getSnsToken();
	}

	/*
	 * 获取用户授权的个人信息
	 * 
	 * @param snsToken
	 * @return
	 */
	public String get_sns_userinfo_unionid(String snsToken) throws ApiException{
		OapiSnsGetuserinfoResponse response = null;
		try {
			DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/sns/getuserinfo");
			OapiSnsGetuserinfoRequest request = new OapiSnsGetuserinfoRequest();
			request.setSnsToken(snsToken);
			request.setHttpMethod(METHOD_GET);
			response = client.execute(request);
		} catch (ApiException e) {
			e.printStackTrace();
		}
		return response.getBody();
	}

	/*
	 * 根据钉钉的userid拿取用户的详细信息(包括手机号，部门id，等)
	 * https://open-doc.dingtalk.com/microapp/serverapi2/ege851
	 * @throws ApiException 
	 */
	public OapiUserGetResponse getUserByUserid( String userid, String accessToken) throws ApiException {
		
		DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/user/get");
		OapiUserGetRequest request = new OapiUserGetRequest();
		request.setUserid(userid);
		request.setHttpMethod(METHOD_GET);
		
		return client.execute(request, accessToken);
	}

	/*
	 * 获取部门详情（根据部门id查询）
	 * 
	 * @param accessToken
	 * @param deptid
	 * @return
	 * @throws ApiException
	 */
	public OapiDepartmentGetResponse getDepartmentInfo(String accessToken, String deptid) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/department/get");
		OapiDepartmentGetRequest request = new OapiDepartmentGetRequest();
		request.setId(deptid);
		request.setHttpMethod(METHOD_GET);
		return client.execute(request, accessToken);
	}

	
	/*
	   * 获得ticket,不强制刷新ticket.
	   *
	   * @see #getTicket(TicketType, boolean)
	   */
	  String getJsapiTicket(TicketType type, String accessToken) throws ApiException {
		  DefaultDingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/get_jsapi_ticket");
		  OapiGetJsapiTicketRequest req = new OapiGetJsapiTicketRequest();
		  req.setTopHttpMethod("GET");
		  OapiGetJsapiTicketResponse execute = client.execute(req, accessToken);
		  return execute.getTicket();
	  }
	   
	  
	/*
	 *	 创建调用jsapi时所需要的签名.
	 * 	详情请见：https://ding-doc.dingtalk.com/doc#/dev/uwa7vs
	 */
	public JsapiTicketSignature createJsapiSignature(String url, String agentId, String accessToken) throws ApiException {
		
		long timestamp = System.currentTimeMillis() / 1000;
	    String randomStr = RandomUtils.getRandomStr();
	    String jsapiTicket = getJsapiTicket(TicketType.JSAPI, accessToken);
	    String signature = DingTalkUtils.sign(jsapiTicket, randomStr, timestamp, url);
	    JsapiTicketSignature jsapiSignature = new JsapiTicketSignature();
	    jsapiSignature.setAgentId(agentId);
	    jsapiSignature.setTimestamp(timestamp);
	    jsapiSignature.setNonceStr(randomStr);
	    jsapiSignature.setUrl(url);
	    jsapiSignature.setSignature(signature);
	    return jsapiSignature;
		
	}
	

}
