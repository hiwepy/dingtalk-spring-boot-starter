package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.property.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DefaultDingTalkConfigProvider}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class DefaultDingTalkConfigProviderTest {

    private DingTalkProperties properties;
    private DefaultDingTalkConfigProvider provider;

    @BeforeEach
    void setUp() throws Exception {
        properties = new DingTalkProperties();
        properties.setCorpId("testCorpId");
        properties.setCorpSecret("testCorpSecret");

        // CorpApps
        DingTalkCorpAppProperties corpApp = new DingTalkCorpAppProperties();
        corpApp.setAgentId("agent1");
        corpApp.setAppKey("appKey1");
        corpApp.setAppSecret("corpAppSecret1");
        properties.setCorpApps(Arrays.asList(corpApp));

        // PersonalMiniApps
        DingTalkPersonalMiniAppProperties miniApp = new DingTalkPersonalMiniAppProperties();
        miniApp.setAppId("miniAppId1");
        miniApp.setAppSecret("miniAppSecret1");
        properties.setApps(Arrays.asList(miniApp));

        // Suites
        DingTalkSuiteProperties suite = new DingTalkSuiteProperties();
        suite.setSuiteId("suiteId1");
        suite.setAppId("suiteAppId1");
        suite.setSuiteKey("suiteKey1");
        suite.setSuiteSecret("suiteSecret1");
        properties.setSuites(Arrays.asList(suite));

        // Logins
        DingTalkLoginProperties login = new DingTalkLoginProperties();
        login.setAppId("loginAppId1");
        login.setAppSecret("loginAppSecret1");
        properties.setLogins(Arrays.asList(login));

        // Robots
        DingTalkRobotProperties robot = new DingTalkRobotProperties();
        robot.setRobotId("robotId1");
        robot.setAccessToken("robotAccessToken1");
        robot.setSecretToken("robotSecretToken1");
        properties.setRobots(Arrays.asList(robot));

        provider = new DefaultDingTalkConfigProvider(properties);
        provider.afterPropertiesSet();
    }

    @Test
    void afterPropertiesSet_shouldLoadCorpAppKeys() {
        assertThat(provider.hasAppKey("appKey1")).isTrue();
        assertThat(provider.hasAppKey("nonExistent")).isFalse();
    }

    @Test
    void afterPropertiesSet_shouldLoadMiniAppKeys() {
        assertThat(provider.hasAppKey("miniAppId1")).isTrue();
    }

    @Test
    void afterPropertiesSet_shouldLoadSuiteKeys() {
        assertThat(provider.hasAppKey("suiteAppId1")).isTrue();
    }

    @Test
    void afterPropertiesSet_shouldLoadLoginKeys() {
        assertThat(provider.hasAppKey("loginAppId1")).isTrue();
    }

    @Test
    void getDingTalkProperties_shouldReturnProperties() {
        DingTalkProperties result = provider.getDingTalkProperties("anyCorpId");
        assertThat(result).isSameAs(properties);
    }

    @Test
    void getDingTalkCorpAppProperties_shouldFindByAgentId() {
        DingTalkCorpAppProperties result = provider.getDingTalkCorpAppProperties("corpId", "agent1");
        assertThat(result).isNotNull();
        assertThat(result.getAppKey()).isEqualTo("appKey1");
    }

    @Test
    void getDingTalkCorpAppProperties_shouldReturnNullWhenNotFound() {
        DingTalkCorpAppProperties result = provider.getDingTalkCorpAppProperties("corpId", "nonExistent");
        assertThat(result).isNull();
    }

    @Test
    void getDingTalkCorpAppProperties_shouldReturnNullWhenListEmpty() throws Exception {
        DingTalkProperties emptyProps = new DingTalkProperties();
        emptyProps.setCorpApps(Collections.emptyList());
        DefaultDingTalkConfigProvider emptyProvider = new DefaultDingTalkConfigProvider(emptyProps);
        emptyProvider.afterPropertiesSet();
        assertThat(emptyProvider.getDingTalkCorpAppProperties("corpId", "agent1")).isNull();
    }

    @Test
    void getDingTalkPersonalMiniAppProperties_shouldFindByAppId() {
        DingTalkPersonalMiniAppProperties result = provider.getDingTalkPersonalMiniAppProperties("corpId", "miniAppId1");
        assertThat(result).isNotNull();
        assertThat(result.getAppSecret()).isEqualTo("miniAppSecret1");
    }

    @Test
    void getDingTalkPersonalMiniAppProperties_shouldReturnNullWhenNotFound() {
        assertThat(provider.getDingTalkPersonalMiniAppProperties("corpId", "nonExistent")).isNull();
    }

    @Test
    void getDingTalkPersonalMiniAppProperties_shouldReturnNullWhenListEmpty() throws Exception {
        DingTalkProperties emptyProps = new DingTalkProperties();
        emptyProps.setApps(Collections.emptyList());
        DefaultDingTalkConfigProvider emptyProvider = new DefaultDingTalkConfigProvider(emptyProps);
        emptyProvider.afterPropertiesSet();
        assertThat(emptyProvider.getDingTalkPersonalMiniAppProperties("corpId", "appId")).isNull();
    }

    @Test
    void getDingTalkSuiteProperties_shouldFindBySuiteId() {
        DingTalkSuiteProperties result = provider.getDingTalkSuiteProperties("corpId", "suiteId1");
        assertThat(result).isNotNull();
        assertThat(result.getSuiteSecret()).isEqualTo("suiteSecret1");
    }

    @Test
    void getDingTalkSuiteProperties_shouldReturnNullWhenNotFound() {
        assertThat(provider.getDingTalkSuiteProperties("corpId", "nonExistent")).isNull();
    }

    @Test
    void getDingTalkSuiteProperties_shouldReturnNullWhenListEmpty() throws Exception {
        DingTalkProperties emptyProps = new DingTalkProperties();
        emptyProps.setSuites(Collections.emptyList());
        DefaultDingTalkConfigProvider emptyProvider = new DefaultDingTalkConfigProvider(emptyProps);
        emptyProvider.afterPropertiesSet();
        assertThat(emptyProvider.getDingTalkSuiteProperties("corpId", "suiteId")).isNull();
    }

    @Test
    void getDingTalkLoginProperties_shouldFindByAppId() {
        DingTalkLoginProperties result = provider.getDingTalkLoginProperties("corpId", "loginAppId1");
        assertThat(result).isNotNull();
        assertThat(result.getAppSecret()).isEqualTo("loginAppSecret1");
    }

    @Test
    void getDingTalkLoginProperties_shouldReturnNullWhenNotFound() {
        assertThat(provider.getDingTalkLoginProperties("corpId", "nonExistent")).isNull();
    }

    @Test
    void getDingTalkLoginProperties_shouldReturnNullWhenListEmpty() throws Exception {
        DingTalkProperties emptyProps = new DingTalkProperties();
        emptyProps.setLogins(Collections.emptyList());
        DefaultDingTalkConfigProvider emptyProvider = new DefaultDingTalkConfigProvider(emptyProps);
        emptyProvider.afterPropertiesSet();
        assertThat(emptyProvider.getDingTalkLoginProperties("corpId", "appId")).isNull();
    }

    @Test
    void getDingTalkRobotProperties_shouldFindByRobotId() {
        DingTalkRobotProperties result = provider.getDingTalkRobotProperties("corpId", "robotId1");
        assertThat(result).isNotNull();
        assertThat(result.getAccessToken()).isEqualTo("robotAccessToken1");
    }

    @Test
    void getDingTalkRobotProperties_shouldReturnNullWhenNotFound() {
        assertThat(provider.getDingTalkRobotProperties("corpId", "nonExistent")).isNull();
    }

    @Test
    void getDingTalkRobotProperties_shouldReturnNullWhenListEmpty() throws Exception {
        DingTalkProperties emptyProps = new DingTalkProperties();
        emptyProps.setRobots(Collections.emptyList());
        DefaultDingTalkConfigProvider emptyProvider = new DefaultDingTalkConfigProvider(emptyProps);
        emptyProvider.afterPropertiesSet();
        assertThat(emptyProvider.getDingTalkRobotProperties("corpId", "robotId")).isNull();
    }

    @Test
    void getCorpId_shouldReturnConfiguredCorpId() {
        assertThat(provider.getCorpId("anyKey")).isEqualTo("testCorpId");
    }

    @Test
    void getCorpSecret_shouldReturnConfiguredCorpSecret() {
        assertThat(provider.getCorpSecret("anyCorpId")).isEqualTo("testCorpSecret");
    }

    @Test
    void getAppSecret_shouldReturnSecretFromMap() {
        assertThat(provider.getAppSecret("corpId", "appKey1")).isEqualTo("corpAppSecret1");
    }

    @Test
    void getAppSecret_shouldReturnNullForUnknownKey() {
        assertThat(provider.getAppSecret("corpId", "unknown")).isNull();
    }

    @Test
    void afterPropertiesSet_withNullLists_shouldNotFail() throws Exception {
        DingTalkProperties nullListProps = new DingTalkProperties();
        nullListProps.setCorpId("corp");
        nullListProps.setCorpSecret("secret");
        // All lists are null by default
        DefaultDingTalkConfigProvider nullProvider = new DefaultDingTalkConfigProvider(nullListProps);
        nullProvider.afterPropertiesSet();
        assertThat(nullProvider.hasAppKey("any")).isFalse();
    }
}
