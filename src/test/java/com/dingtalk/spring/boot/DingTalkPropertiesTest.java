package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.property.*;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DingTalkProperties} and property classes.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkPropertiesTest {

    @Test
    void prefix_shouldBeDingtalk() {
        assertThat(DingTalkProperties.PREFIX).isEqualTo("dingtalk");
    }

    @Test
    void dingTalkProperties_settersAndGetters() {
        DingTalkProperties props = new DingTalkProperties();
        props.setCorpId("corp1");
        props.setCorpSecret("secret1");

        DingTalkCorpAppProperties corpApp = new DingTalkCorpAppProperties();
        corpApp.setAgentId("a1");
        corpApp.setAppKey("k1");
        corpApp.setAppSecret("s1");
        props.setCorpApps(Arrays.asList(corpApp));

        DingTalkPersonalMiniAppProperties miniApp = new DingTalkPersonalMiniAppProperties();
        miniApp.setAppId("ma1");
        miniApp.setAppSecret("ms1");
        props.setApps(Arrays.asList(miniApp));

        DingTalkSuiteProperties suite = new DingTalkSuiteProperties();
        suite.setSuiteId("sid1");
        suite.setAppId("aid1");
        suite.setSuiteKey("sk1");
        suite.setSuiteSecret("ss1");
        props.setSuites(Arrays.asList(suite));

        DingTalkLoginProperties login = new DingTalkLoginProperties();
        login.setAppId("la1");
        login.setAppSecret("ls1");
        props.setLogins(Arrays.asList(login));

        DingTalkRobotProperties robot = new DingTalkRobotProperties();
        robot.setRobotId("rid1");
        robot.setAccessToken("at1");
        robot.setSecretToken("st1");
        props.setRobots(Arrays.asList(robot));

        assertThat(props.getCorpId()).isEqualTo("corp1");
        assertThat(props.getCorpSecret()).isEqualTo("secret1");
        assertThat(props.getCorpApps()).hasSize(1);
        assertThat(props.getApps()).hasSize(1);
        assertThat(props.getSuites()).hasSize(1);
        assertThat(props.getLogins()).hasSize(1);
        assertThat(props.getRobots()).hasSize(1);
    }

    @Test
    void dingTalkCorpAppProperties_settersAndGetters() {
        DingTalkCorpAppProperties props = new DingTalkCorpAppProperties();
        props.setAgentId("a1");
        props.setAppKey("k1");
        props.setAppSecret("s1");
        assertThat(props.getAgentId()).isEqualTo("a1");
        assertThat(props.getAppKey()).isEqualTo("k1");
        assertThat(props.getAppSecret()).isEqualTo("s1");
        assertThat(props.toString()).contains("agentId=a1");
    }

    @Test
    void dingTalkPersonalMiniAppProperties_settersAndGetters() {
        DingTalkPersonalMiniAppProperties props = new DingTalkPersonalMiniAppProperties();
        props.setAppId("a1");
        props.setAppSecret("s1");
        assertThat(props.getAppId()).isEqualTo("a1");
        assertThat(props.getAppSecret()).isEqualTo("s1");
        assertThat(props.toString()).contains("appId=a1");
    }

    @Test
    void dingTalkSuiteProperties_settersAndGetters() {
        DingTalkSuiteProperties props = new DingTalkSuiteProperties();
        props.setSuiteId("sid1");
        props.setAppId("aid1");
        props.setSuiteKey("sk1");
        props.setSuiteSecret("ss1");
        assertThat(props.getSuiteId()).isEqualTo("sid1");
        assertThat(props.getAppId()).isEqualTo("aid1");
        assertThat(props.getSuiteKey()).isEqualTo("sk1");
        assertThat(props.getSuiteSecret()).isEqualTo("ss1");
        assertThat(props.toString()).contains("suiteId=sid1");
    }

    @Test
    void dingTalkLoginProperties_settersAndGetters() {
        DingTalkLoginProperties props = new DingTalkLoginProperties();
        props.setAppId("a1");
        props.setAppSecret("s1");
        assertThat(props.getAppId()).isEqualTo("a1");
        assertThat(props.getAppSecret()).isEqualTo("s1");
        assertThat(props.toString()).contains("appId=a1");
    }

    @Test
    void dingTalkRobotProperties_settersAndGetters() {
        DingTalkRobotProperties props = new DingTalkRobotProperties();
        props.setRobotId("r1");
        props.setAccessToken("at1");
        props.setSecretToken("st1");
        assertThat(props.getRobotId()).isEqualTo("r1");
        assertThat(props.getAccessToken()).isEqualTo("at1");
        assertThat(props.getSecretToken()).isEqualTo("st1");
        assertThat(props.toString()).contains("robotId=r1");
    }
}
