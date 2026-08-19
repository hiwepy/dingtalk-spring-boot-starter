package com.dingtalk.spring.boot;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSnsGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSnsGettokenResponse;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;

/**
 * Default implementation of {@link DingTalkAccessTokenProvider} that retrieves access tokens
 * from the DingTalk Open API using the configured app key and secret.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class DefaultDingTalkAccessTokenProvider implements DingTalkAccessTokenProvider {

    private final String DINGTALK_SERVICE = "https://oapi.dingtalk.com";
    private final String METHOD_GET = "GET";
    private final DingTalkConfigProvider dingTalkConfigProvider;

    public DefaultDingTalkAccessTokenProvider(DingTalkConfigProvider dingTalkConfigProvider) {
        this.dingTalkConfigProvider = dingTalkConfigProvider;
    }

    /**
     * Retrieves the enterprise internal application access token by app key and secret.
     *
     * @param corpId the enterprise ID
     * @param appKey the application key
     * @return the access token, or null if the request fails
     * @throws ApiException if the API request fails
     */
    @Override
    /** @return return the access token. */
    public String getAccessToken(String corpId, String appKey) throws ApiException {

        OapiGettokenRequest request = new OapiGettokenRequest();

        String appSecret = dingTalkConfigProvider.getAppSecret(corpId, appKey);

        request.setAppkey(appKey);
        request.setAppsecret(appSecret);
        request.setHttpMethod(METHOD_GET);

        DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/gettoken");
        OapiGettokenResponse response = client.execute(request);

        if (response.isSuccess()) {
            return response.getAccessToken();
        }
        return null;
    }

    /**
     * Retrieves the SNS (social) access token for a DingTalk open application.
     *
     * @param corpId the enterprise ID
     * @param appId the application ID
     * @return the SNS access token, or an empty string if the request fails
     * @throws ApiException if the API request fails
     */
    @Override
    /** @return return the sns access token. */
    public String getSnsAccessToken(String corpId, String appId) throws ApiException {

        String appSecret = dingTalkConfigProvider.getAppSecret(corpId, appId);

        OapiSnsGettokenRequest request = new OapiSnsGettokenRequest();

        request.setAppid(appId);
        request.setAppsecret(appSecret);
        request.setHttpMethod(METHOD_GET);

        DingTalkClient client = new DefaultDingTalkClient(DINGTALK_SERVICE + "/sns/gettoken");

        OapiSnsGettokenResponse response = client.execute(request);

        if (response.isSuccess()) {
            return response.getAccessToken();
        }
        return StringUtils.EMPTY;

    }


}
