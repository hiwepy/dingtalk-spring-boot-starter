package com.dingtalk.spring.boot;

import org.apache.commons.lang3.StringUtils;

public interface DingTalkUserIdProvider {

    default String getUserIdByDingTalkUser(String corpId, String appId, String account)  {
        return account;
    }

    default String getDingTalkUserByUserId(String corpId, String appId, String... userIds) {
        return StringUtils.join(userIds, ",");
    }

}
