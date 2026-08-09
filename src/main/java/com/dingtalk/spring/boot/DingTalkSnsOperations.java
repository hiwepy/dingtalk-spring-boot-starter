package com.dingtalk.spring.boot;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSnsGetPersistentCodeRequest;
import com.dingtalk.api.request.OapiSnsGetSnsTokenRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoBycodeRequest;
import com.dingtalk.api.request.OapiSnsGetuserinfoRequest;
import com.dingtalk.api.response.OapiSnsGetPersistentCodeResponse;
import com.dingtalk.api.response.OapiSnsGetSnsTokenResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoBycodeResponse;
import com.dingtalk.api.response.OapiSnsGetuserinfoResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

/**
 * Operations for DingTalk SNS (social) login, including scan-to-login third-party websites,
 * free-login within DingTalk, and password-based login for third-party websites.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/kymkv6">Scan-to-Login Third-Party Website</a>
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/etaarr">Free-Login Third-Party Website in DingTalk</a>
 * @see <a href="https://ding-doc.dingtalk.com/doc#/serverapi2/hmxp3f">Password Login Third-Party Website</a>
 */
@Slf4j
public class DingTalkSnsOperations extends DingTalkOperations {

	public DingTalkSnsOperations(DingTalkTemplate template) {
		super(template);
	}

	/**
	 * Retrieves user information by a temporary authorization code for third-party scan-to-login.
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

	/**
	 * Retrieves the SNS token using the open ID and persistent code.
	 *
	 * @param openId         the user's open ID
	 * @param persistentCode the persistent authorization code
	 * @param accessToken    the open application access token
	 * @return the SNS token
	 * @throws ApiException if the API request fails
	 */
	public String getSnsToken(String openId, String persistentCode, String accessToken) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/sns/get_sns_token");
		OapiSnsGetSnsTokenRequest request = new OapiSnsGetSnsTokenRequest();
		request.setOpenid(openId);
		request.setPersistentCode(persistentCode);
		OapiSnsGetSnsTokenResponse response = client.execute(request, accessToken);
		return response.getSnsToken();
	}

	/**
	 * Retrieves the authorized user's personal information using the SNS token.
	 *
	 * @param snsToken the SNS token
	 * @return the response body containing user information
	 * @throws ApiException if the API request fails
	 */
	public String getUserinfo(String snsToken) throws ApiException{
		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/sns/getuserinfo");
		OapiSnsGetuserinfoRequest request = new OapiSnsGetuserinfoRequest();
		request.setSnsToken(snsToken);
		request.setHttpMethod(METHOD_GET);
		OapiSnsGetuserinfoResponse response = client.execute(request);
		return response.getBody();
	}

}
