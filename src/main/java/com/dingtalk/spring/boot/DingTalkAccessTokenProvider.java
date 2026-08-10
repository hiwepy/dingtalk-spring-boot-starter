package com.dingtalk.spring.boot;

import com.taobao.api.ApiException;

/**
 * Provider interface for obtaining DingTalk access tokens for enterprise internal applications
 * and open (SNS) applications.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public interface DingTalkAccessTokenProvider {

    /**
     * Retrieves the enterprise internal application access token using the given corp ID and app key.
     * @see <a href="https://open.dingtalk.com/document/isvapp-server/obtain-the-access_token-of-an-enterprise-s-internal-applications">
     *      Obtain the Access Token of an Enterprise's Internal Applications</a>
     *
     * @param corpId  the enterprise ID
     * @param appKey  the application key
     * @return the access token
     * @throws ApiException if the API request fails
     */
    String getAccessToken(String corpId, String appKey) throws ApiException;

    /**
     * Retrieves the SNS (social) access token for a DingTalk open application.
     *
     * @param corpId  the enterprise ID
     * @param appId   the application ID
     * @return the SNS access token
     * @throws ApiException if the API request fails
     */
    String getSnsAccessToken(String corpId, String appId) throws ApiException;

}
