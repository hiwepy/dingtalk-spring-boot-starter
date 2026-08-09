package com.dingtalk.spring.boot;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DingTalkUserIdProvider} default methods.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkUserIdProviderTest {

    private final DingTalkUserIdProvider provider = new DingTalkUserIdProvider() {};

    @Test
    void getUserIdByDingTalkUser_shouldReturnAccount() {
        String result = provider.getUserIdByDingTalkUser("corpId", "appId", "user123");
        assertThat(result).isEqualTo("user123");
    }

    @Test
    void getDingTalkUserByUserId_shouldJoinUserIds() {
        String result = provider.getDingTalkUserByUserId("corpId", "appId", "u1", "u2", "u3");
        assertThat(result).isEqualTo("u1,u2,u3");
    }

    @Test
    void getDingTalkUserByUserId_withSingleId_shouldReturnId() {
        String result = provider.getDingTalkUserByUserId("corpId", "appId", "u1");
        assertThat(result).isEqualTo("u1");
    }
}
