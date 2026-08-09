package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.property.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultDingTalkAccessTokenProvider}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DefaultDingTalkAccessTokenProviderTest {

    private DefaultDingTalkAccessTokenProvider provider;

    @BeforeEach
    void setUp() throws Exception {
        DingTalkProperties properties = new DingTalkProperties();
        properties.setCorpId("corpId");
        properties.setCorpSecret("corpSecret");
        DingTalkCorpAppProperties corpApp = new DingTalkCorpAppProperties();
        corpApp.setAgentId("agent1");
        corpApp.setAppKey("appKey1");
        corpApp.setAppSecret("appSecret1");
        properties.setCorpApps(Arrays.asList(corpApp));
        DefaultDingTalkConfigProvider configProvider = new DefaultDingTalkConfigProvider(properties);
        configProvider.afterPropertiesSet();
        provider = new DefaultDingTalkAccessTokenProvider(configProvider);
    }

    @Test
    void constructor_shouldAcceptConfigProvider() {
        assertThat(provider).isNotNull();
    }

    @Test
    void getAccessToken_shouldReturnNullOnApiFailure() throws Exception {
        // This will fail because we're not connected to a real DingTalk server
        String token = provider.getAccessToken("corpId", "appKey1");
        assertThat(token).isNull();
    }

    @Test
    void getSnsAccessToken_shouldReturnEmptyOnApiFailure() throws Exception {
        // This will fail because we're not connected to a real DingTalk server
        String token = provider.getSnsAccessToken("corpId", "appKey1");
        assertThat(token).isEmpty();
    }
}
