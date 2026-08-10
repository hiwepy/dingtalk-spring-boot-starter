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
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetUseridByUnionidRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetUseridByUnionidResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

/**
 * Operations for DingTalk enterprise internal application login (free login) and user management.
 * Provides methods to retrieve user information by authorization code, union ID, or user ID.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/clotub">Enterprise Internal Application Free Login</a>
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/ege851">User Management</a>
 */
@Slf4j
public class DingTalkAccountOperations extends DingTalkOperations {

	public DingTalkAccountOperations(DingTalkTemplate template) {
		super(template);
	}

	/**
	 * Retrieves user information by the free-login authorization code and access token
	 * for enterprise internal applications.
	 *
	 * @param code          the free-login authorization code
	 * @param accessToken   the API access token
	 * @return the user information response
	 * @throws ApiException if the API request fails
	 */
	public OapiUserGetuserinfoResponse getUserinfoBycode( String code, String accessToken) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/user/getuserinfo");
		OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
		request.setCode(code);
		request.setHttpMethod(METHOD_GET);
		return client.execute(request, accessToken);
	}
	
	
	/**
	 * Retrieves the DingTalk user ID by the given union ID.
	 *
	 * @param unionid       the union ID of the user
	 * @param accessToken   the API access token
	 * @return the response containing the user ID
	 * @throws ApiException if the API request fails
	 */
	public OapiUserGetUseridByUnionidResponse getUseridByUnionid( String unionid, String accessToken) throws ApiException {
		
		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/user/getUseridByUnionid");
		OapiUserGetUseridByUnionidRequest request = new OapiUserGetUseridByUnionidRequest();
		request.setUnionid(unionid);
		request.setHttpMethod(METHOD_GET);
		
		return client.execute(request, accessToken);
	}
	
	/**
	 * Retrieves detailed user information (including phone number, department ID, etc.)
	 * by the DingTalk user ID.
	 *
	 * @param userid        the DingTalk user ID
	 * @param accessToken   the API access token
	 * @return the user detail response
	 * @throws ApiException if the API request fails
	 */
	public OapiUserGetResponse getUserByUserid( String userid, String accessToken) throws ApiException {
		
		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/user/get");
		OapiUserGetRequest request = new OapiUserGetRequest();
		request.setUserid(userid);
		request.setHttpMethod(METHOD_GET);
		
		return client.execute(request, accessToken);
	}

	
}
