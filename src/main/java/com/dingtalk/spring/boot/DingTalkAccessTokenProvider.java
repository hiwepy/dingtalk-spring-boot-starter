package com.dingtalk.spring.boot;

import com.taobao.api.ApiException;

public interface DingTalkAccessTokenProvider {

    /**
     * 根据cropId获取企业内部开发access_token
     * https://open-doc.dingtalk.com/microapp/serverapi2/eev437
     * @param cropId  企业ID
     * @param appKey    企业Id
     * @return the AccessToken
     * @throws ApiException if get AccessToken Exception
     */
    String getAccessToken(String cropId, String appKey) throws ApiException;

    /**
     * 获取钉钉开放应用的ACCESS_TOKEN
     * @param cropId  企业ID
     * @param appId   企业应用Id
     * @return the AccessToken
     * @throws ApiException if get AccessToken Exception
     */
    String getSnsAccessToken(String cropId, String appId) throws ApiException;

}
