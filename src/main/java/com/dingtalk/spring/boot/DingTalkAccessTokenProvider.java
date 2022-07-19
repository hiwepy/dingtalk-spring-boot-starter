package com.dingtalk.spring.boot;

import com.taobao.api.ApiException;

public interface DingTalkAccessTokenProvider {

    /**
     * 根据corpId获取企业内部开发access_token
     * https://open.dingtalk.com/document/isvapp-server/obtain-the-access_token-of-an-enterprise-s-internal-applications
     * @param corpId  企业ID
     * @param appKey    企业Id
     * @return the AccessToken
     * @throws ApiException if get AccessToken Exception
     */
    String getAccessToken(String corpId, String appKey) throws ApiException;

    /**
     * 获取钉钉开放应用的ACCESS_TOKEN
     * @param corpId  企业ID
     * @param appId   企业应用Id
     * @return the AccessToken
     * @throws ApiException if get AccessToken Exception
     */
    String getSnsAccessToken(String corpId, String appId) throws ApiException;

}
