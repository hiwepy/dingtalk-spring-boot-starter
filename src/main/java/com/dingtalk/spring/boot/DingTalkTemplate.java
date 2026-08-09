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

/**
 * Central template class for DingTalk API operations. Provides access to account, SNS, SSO,
 * JSAPI, robot, and user operations, as well as access token management and signature computation.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 * @see <a href="https://open-doc.dingtalk.com/microapp/serverapi2/eev437">Access Token Documentation</a>
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

	public boolean hasAppKey(String appKey) {
		return dingTalkConfigProvider.hasAppKey(appKey);
	}

	/**
	 * Retrieves the enterprise corpId for the given application key.
	 *
	 * @param appKey the application key or ID
	 * @return the enterprise corpId
	 */
	public String getCorpId(String appKey){
		return dingTalkConfigProvider.getCorpId(appKey);
	}

	/**
	 * Retrieves the enterprise secret for the given corpId.
	 *
	 * @param corpId the enterprise ID
	 * @return the enterprise secret
	 */
	public String getCorpSecret(String corpId){
		return dingTalkConfigProvider.getCorpSecret(corpId);
	}

	/**
	 * Retrieves the application secret for the given enterprise and application key.
	 *
	 * @param corpId the enterprise ID
	 * @param appKey the application key or ID
	 * @return the application secret
	 */
	public String getAppSecret(String corpId, String appKey) {
		return dingTalkConfigProvider.getAppSecret(corpId, appKey);
	}

	/**
	 * Retrieves the enterprise internal application access token.
	 *
	 * @param corpId  the enterprise ID
	 * @param appKey  the application key
	 * @return the access token
	 * @throws ApiException if the API request fails
	 */
	public String getAccessToken(String corpId, String appKey) throws ApiException {
		return dingTalkAccessTokenProvider.getAccessToken(corpId, appKey);
	}
	
	/**
	 * Retrieves the SNS access token for a DingTalk open application.
	 *
	 * @param corpId the enterprise ID
	 * @param appId  the application ID
	 * @return the SNS access token
	 * @throws ApiException if the API request fails
	 */
	public String getSnsAccessToken(String corpId, String appId) throws ApiException {
		return dingTalkAccessTokenProvider.getSnsAccessToken(corpId, appId);
	}
	
	/**
     * Computes the HMAC-SHA256 signature for DingTalk robot message verification.
     *
     * @param secret    the robot secret token (SEC开头的字符)
     * @param timestamp the current timestamp in milliseconds
     * @return the URL-encoded signature string
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
