package com.dingtalk.spring.boot;

import org.apache.commons.lang3.StringUtils;

/**
 * Provider interface for mapping between DingTalk user IDs and internal application user IDs.
 * Implementations can customize the user ID resolution logic for different DingTalk application types.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public interface DingTalkUserIdProvider {

    default String getUserIdByDingTalkUser(String corpId, String appId, String account)  {
        return account;
    }

    default String getDingTalkUserByUserId(String corpId, String appId, String... userIds) {
        return StringUtils.join(userIds, ",");
    }

}
