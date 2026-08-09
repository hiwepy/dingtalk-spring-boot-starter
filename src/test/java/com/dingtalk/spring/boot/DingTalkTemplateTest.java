package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.bean.JsapiTicketSignature;
import com.dingtalk.spring.boot.property.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DingTalkTemplate}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkTemplateTest {

    private DingTalkTemplate template;
    private DingTalkProperties properties;
    private DefaultDingTalkConfigProvider configProvider;
    private DefaultDingTalkAccessTokenProvider accessTokenProvider;

    @BeforeEach
    void setUp() throws Exception {
        properties = new DingTalkProperties();
        properties.setCorpId("testCorpId");
        properties.setCorpSecret("testCorpSecret");

        DingTalkCorpAppProperties corpApp = new DingTalkCorpAppProperties();
        corpApp.setAgentId("agent1");
        corpApp.setAppKey("appKey1");
        corpApp.setAppSecret("appSecret1");
        properties.setCorpApps(Arrays.asList(corpApp));

        DingTalkRobotProperties robot = new DingTalkRobotProperties();
        robot.setRobotId("robot1");
        robot.setAccessToken("robotToken1");
        robot.setSecretToken("SECrobotSecret1");
        properties.setRobots(Arrays.asList(robot));

        configProvider = new DefaultDingTalkConfigProvider(properties);
        configProvider.afterPropertiesSet();

        accessTokenProvider = new DefaultDingTalkAccessTokenProvider(configProvider);
        template = new DingTalkTemplate(configProvider, accessTokenProvider);
        template.afterPropertiesSet();
    }

    @Test
    void hasAppKey_shouldDelegateToConfigProvider() {
        assertThat(template.hasAppKey("appKey1")).isTrue();
        assertThat(template.hasAppKey("nonExistent")).isFalse();
    }

    @Test
    void getCorpId_shouldDelegateToConfigProvider() {
        assertThat(template.getCorpId("appKey1")).isEqualTo("testCorpId");
    }

    @Test
    void getCorpSecret_shouldDelegateToConfigProvider() {
        assertThat(template.getCorpSecret("corpId")).isEqualTo("testCorpSecret");
    }

    @Test
    void getAppSecret_shouldDelegateToConfigProvider() {
        assertThat(template.getAppSecret("corpId", "appKey1")).isEqualTo("appSecret1");
    }

    @Test
    void getAccessTokenProvider_shouldReturnProvider() {
        assertThat(template.getDingTalkAccessTokenProvider()).isSameAs(accessTokenProvider);
    }

    @Test
    void getConfigProvider_shouldReturnProvider() {
        assertThat(template.getDingTalkConfigProvider()).isSameAs(configProvider);
    }

    @Test
    void opsForAccount_shouldReturnNonNull() {
        assertThat(template.opsForAccount()).isNotNull();
    }

    @Test
    void opsForSns_shouldReturnNonNull() {
        assertThat(template.opsForSns()).isNotNull();
    }

    @Test
    void opsForSso_shouldReturnNonNull() {
        assertThat(template.opsForSso()).isNotNull();
    }

    @Test
    void opsForJsapi_shouldReturnNonNull() {
        assertThat(template.opsForJsapi()).isNotNull();
    }

    @Test
    void opsForRobot_shouldReturnNonNull() {
        assertThat(template.opsForRobot()).isNotNull();
    }

    @Test
    void opsForUser_shouldReturnNonNull() {
        assertThat(template.opsForUser()).isNotNull();
    }

    @Test
    void getSign_shouldReturnNonNullSignature() {
        String sign = template.getSign("SECtestSecret", System.currentTimeMillis());
        assertThat(sign).isNotNull();
        assertThat(sign).isNotEmpty();
    }

    @Test
    void getSign_shouldReturnConsistentSignature() {
        long timestamp = 1234567890L;
        String sign1 = template.getSign("SECtestSecret", timestamp);
        String sign2 = template.getSign("SECtestSecret", timestamp);
        assertThat(sign1).isEqualTo(sign2);
    }

    @Test
    void getSign_shouldReturnDifferentSignatureForDifferentTimestamps() {
        String sign1 = template.getSign("SECtestSecret", 1000L);
        String sign2 = template.getSign("SECtestSecret", 2000L);
        assertThat(sign1).isNotEqualTo(sign2);
    }

    @Test
    void afterPropertiesSet_shouldNotThrow() throws Exception {
        DingTalkTemplate newTemplate = new DingTalkTemplate(configProvider, accessTokenProvider);
        newTemplate.afterPropertiesSet();
        assertThat(newTemplate).isNotNull();
    }
}
