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

import java.net.URLEncoder;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.InitializingBean;
import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

/*
 * https://open-doc.dingtalk.com/microapp/serverapi2/eev437
 * https://blog.csdn.net/yangguosb/article/details/79762565
 * 
 * @author ： <a href="https://github.com/hiwepy">wandl</a>
 */
@Slf4j
public class DingTalkTemplate implements InitializingBean {

	private final DingTalkConfigProvider dingTalkConfigProvider;
	private final DingTalkAccessTokenProvider dingTalkAccessTokenProvider;
	
	private final DingTalkAccountOperations accountOps = new DingTalkAccountOperations(this);
	private final DingTalkSnsOperations snsOps = new DingTalkSnsOperations(this);
	private final DingTalkSsoOperations ssoOps = new DingTalkSsoOperations(this);
	private final DingTalkJsapiOperations jsapiOps = new DingTalkJsapiOperations(this);
	private final DingTalkRobotOperations robotOps = new DingTalkRobotOperations(this);
	private final DingTalkUserOperations userOps = new DingTalkUserOperations(this);

	public DingTalkTemplate(DingTalkConfigProvider dingTalkConfigProvider, DingTalkAccessTokenProvider dingTalkAccessTokenProvider) {
		this.dingTalkConfigProvider = dingTalkConfigProvider;
		this.dingTalkAccessTokenProvider = dingTalkAccessTokenProvider;
	}

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	public boolean hasAppKey(String cropId, String appKey) {
		return dingTalkConfigProvider.hasAppKey(cropId, appKey);
	}
	
	public String getAppSecret(String cropId, String appKey) {
		return dingTalkConfigProvider.getAppSecret(cropId, appKey);
	} 
	
	/**
	 * 企业内部开发获取access_token 先从缓存查，再到钉钉查
	 * https://open-doc.dingtalk.com/microapp/serverapi2/eev437
	 * @param cropId  企业ID
	 * @param appKey   企业应用Key
	 * @return the AccessToken
	 * @throws ApiException if get AccessToken Exception
	 */
	public String getAccessToken(String cropId, String appKey) throws ApiException {
		return dingTalkAccessTokenProvider.getAccessToken(cropId, appKey);
	}
	
	/**
	 * 获取钉钉开放应用的ACCESS_TOKEN
	 * @param cropId  企业ID
	 * @param appId   企业应用Id
	 * @return the AccessToken
	 * @throws ApiException if get AccessToken Exception
	 */
	public String getSnsAccessToken(String cropId, String appId) throws ApiException {
		return dingTalkAccessTokenProvider.getSnsAccessToken(cropId, appId);
	}
	
	/**
     * 计算签名
     * 参考：https://ding-doc.dingtalk.com/doc#/serverapi2/qf2nxq/9e91d73c
     *
     * @param secret    密钥，机器人安全设置页面，加签一栏下面显示的SEC开头的字符
     * @param timestamp 当前时间戳，毫秒级单位
     * @return 根据时间戳计算后的签名信息
     */
	public String getSign(String secret, Long timestamp) {
        try {
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), "UTF-8");
            log.debug("【发送钉钉群消息】获取到签名sign = {}", sign);
            return sign;
        } catch (Exception e) {
            log.error("【发送钉钉群消息】计算签名异常，errMsg = {}", e);
            return null;
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
	
	public DingTalkRobotOperations opsForRobot() {
		return robotOps;
	}

	public DingTalkUserOperations opsForUser() {
		return userOps;
	}

	public DingTalkAccessTokenProvider getDingTalkAccessTokenProvider() {
		return dingTalkAccessTokenProvider;
	}

	public DingTalkConfigProvider getDingTalkConfigProvider() {
		return dingTalkConfigProvider;
	}

}
