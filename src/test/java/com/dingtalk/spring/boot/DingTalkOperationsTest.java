package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.property.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DingTalkOperations} constants and constructor.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkOperationsTest {

    private DingTalkTemplate template;

    @BeforeEach
    void setUp() throws Exception {
        DingTalkProperties properties = new DingTalkProperties();
        properties.setCorpId("corpId");
        properties.setCorpSecret("corpSecret");
        DingTalkRobotProperties robot = new DingTalkRobotProperties();
        robot.setRobotId("r1");
        robot.setAccessToken("token1");
        robot.setSecretToken("secret1");
        properties.setRobots(Arrays.asList(robot));
        DefaultDingTalkConfigProvider configProvider = new DefaultDingTalkConfigProvider(properties);
        configProvider.afterPropertiesSet();
        DefaultDingTalkAccessTokenProvider tokenProvider = new DefaultDingTalkAccessTokenProvider(configProvider);
        template = new DingTalkTemplate(configProvider, tokenProvider);
    }

    @Test
    void constants_shouldHaveCorrectValues() {
        assertThat(DingTalkOperations.PREFIX).isEqualTo("https://oapi.dingtalk.com");
        assertThat(DingTalkOperations.METHOD_GET).isEqualTo("GET");
        assertThat(DingTalkOperations.APPLICATION_JSON_VALUE).isEqualTo("application/json");
        assertThat(DingTalkOperations.APPLICATION_JSON_UTF8_VALUE).isEqualTo("application/json;charset=UTF-8");
        assertThat(DingTalkOperations.DELIMITER).isEqualTo("&");
        assertThat(DingTalkOperations.SEPARATOR).isEqualTo("=");
    }

    @Test
    void dingTalkAccountOperations_shouldHaveTemplate() {
        DingTalkAccountOperations ops = new DingTalkAccountOperations(template);
        assertThat(ops).isNotNull();
    }

    @Test
    void dingTalkSnsOperations_shouldHaveTemplate() {
        DingTalkSnsOperations ops = new DingTalkSnsOperations(template);
        assertThat(ops).isNotNull();
    }

    @Test
    void dingTalkSsoOperations_shouldHaveTemplate() {
        DingTalkSsoOperations ops = new DingTalkSsoOperations(template);
        assertThat(ops).isNotNull();
    }

    @Test
    void dingTalkJsapiOperations_shouldHaveTemplate() {
        DingTalkJsapiOperations ops = new DingTalkJsapiOperations(template);
        assertThat(ops).isNotNull();
    }

    @Test
    void dingTalkRobotOperations_shouldHaveTemplate() {
        DingTalkRobotOperations ops = new DingTalkRobotOperations(template);
        assertThat(ops).isNotNull();
    }

    @Test
    void dingTalkUserOperations_shouldHaveTemplate() {
        DingTalkUserOperations ops = new DingTalkUserOperations(template);
        assertThat(ops).isNotNull();
    }
}
