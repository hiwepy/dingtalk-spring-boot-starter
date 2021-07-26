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

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.spring.boot.property.DingTalkRobotProperties;
import com.taobao.api.ApiException;

/**
 */
public class DingTalkRobotOperations extends DingTalkOperations {

	public DingTalkRobotOperations(DingTalkTemplate template) {
		super(template);
	}
	
	protected String getWebhook(String robotId, Long timestamp) {
		DingTalkRobotProperties poperties = template.getRobotProperties(robotId);
        StringBuilder serverUrl = new StringBuilder(PREFIX + "/robot/send?access_token=").append(poperties.getAccessToken());
        String sign =  template.getSign(poperties.getSecretToken(), timestamp);
        serverUrl.append("&timestamp=").append(timestamp).append("&sign=").append(sign);
        return serverUrl.toString();
    }
	
	/**
     * 获取用户手机号
     *
     * @param userid
     * @return
     * @throws ApiException
     */
    public String getUserMobile(String access_token, String userid,  String lang) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/topapi/v2/user/get");
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userid);
            req.setLang(lang);
            OapiV2UserGetResponse rsp = client.execute(req, access_token);
            System.out.println(rsp.getBody());
            return rsp.getResult().getMobile();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public OapiRobotSendResponse sendMessage(String robotId, OapiRobotSendRequest request) throws ApiException {
		Long timestamp = System.currentTimeMillis();
		DingTalkClient client = new DefaultDingTalkClient(this.getWebhook(robotId, timestamp));
		request.setTimestamp(timestamp);		
		return client.execute(request);
	}
    
    public OapiRobotSendResponse sendMessageByUrl(String webhook, String secret, OapiRobotSendRequest request) throws ApiException {
		Long timestamp = System.currentTimeMillis();
		
        String sign =  template.getSign(secret, timestamp);
        StringBuilder serverUrl = new StringBuilder(webhook).append("&timestamp=").append(timestamp).append("&sign=").append(sign);
	        
		DingTalkClient client = new DefaultDingTalkClient(serverUrl.toString());
		request.setTimestamp(timestamp);		
		return client.execute(request);
	}
    
}
