package com.dingtalk.spring.boot;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.*;
import com.dingtalk.api.response.*;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

/**
 * Operations for DingTalk user management, supporting free-login for enterprise internal applications,
 * third-party enterprise applications, and the application management backend.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 * @see <a href="https://open.dingtalk.com/document/orgapp-server/enterprise-internal-application-logon-free">Enterprise Internal Application Free Login</a>
 * @see <a href="https://open.dingtalk.com/document/orgapp-server/third-party-enterprise-application-logon-free">Third-Party Enterprise Application Free Login</a>
 * @see <a href="https://open.dingtalk.com/document/orgapp-server/log-on-site-application-management-backend">Application Management Backend Free Login</a>
 */
@Slf4j
public class DingTalkUserOperations extends DingTalkOperations {

	public DingTalkUserOperations(DingTalkTemplate template) {
		super(template);
	}

	/**
	 * Retrieves user information by the free-login authorization code.
	 *
	 * @param code          the free-login authorization code
	 * @param accessToken   the application access token
	 * @return the user information response
	 * @throws ApiException if the API request fails
	 */
	public OapiUserGetuserinfoResponse getUserinfoByCode(String code, String accessToken) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "user/getuserinfo");
		OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
		request.setCode(code);
		return client.execute(request, accessToken);
	}

}
