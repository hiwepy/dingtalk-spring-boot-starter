package com.dingtalk.spring.boot;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSnsGetPersistentCodeRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.response.OapiSnsGetPersistentCodeResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

/**
 * Operations for DingTalk application management backend SSO (single sign-on) free login.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/xswxhg">Application Management Backend Free Login</a>
 */
@Slf4j
public class DingTalkSsoOperations extends DingTalkOperations {

	public DingTalkSsoOperations(DingTalkTemplate template) {
		super(template);
	}
	
	/**
	 * Retrieves user information by a temporary authorization code for SSO free login.
	 * The temporary code can only be used once.
	 *
	 * @param tmp_auth_code the temporary authorization code
	 * @param accessKey     the application ID
	 * @param accessSecret  the application secret
	 * @return the user information response
	 * @throws ApiException if the API request fails
	 */
	public OapiSnsGetuserinfoBycodeResponse getUserinfoByTmpCode( String tmp_auth_code, String accessKey, String accessSecret) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/sns/getuserinfo_bycode");
		OapiSnsGetuserinfoBycodeRequest request = new OapiSnsGetuserinfoBycodeRequest();
		request.setTmpAuthCode(tmp_auth_code);
		return client.execute(request, accessKey, accessSecret);
	}

	/**
	 * Retrieves the persistent authorization code from a temporary authorization code.
	 *
	 * @param tmp_auth_code the temporary authorization code
	 * @param accessToken   the open application access token
	 * @return the response body containing the persistent code
	 * @throws ApiException if the API request fails
	 */
	public String getPersistentCode(String tmp_auth_code, String accessToken) throws ApiException  {
		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/sns/get_persistent_code");
		OapiSnsGetPersistentCodeRequest request = new OapiSnsGetPersistentCodeRequest();
		request.setTmpAuthCode(tmp_auth_code);
		OapiSnsGetPersistentCodeResponse response = client.execute(request, accessToken);
		return response.getBody();
	}
 	
}
