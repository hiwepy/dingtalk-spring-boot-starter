package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.property.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DingTalkAutoConfiguration}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class DingTalkAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(DingTalkAutoConfiguration.class));

    @Test
    void autoConfiguration_shouldCreateBeans() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(DingTalkConfigProvider.class);
            assertThat(context).hasSingleBean(DefaultDingTalkConfigProvider.class);
            assertThat(context).hasSingleBean(DingTalkAccessTokenProvider.class);
            assertThat(context).hasSingleBean(DefaultDingTalkAccessTokenProvider.class);
            assertThat(context).hasSingleBean(DingTalkTemplate.class);
        });
    }

    @Test
    void autoConfiguration_shouldBindProperties() {
        contextRunner
                .withPropertyValues(
                        "dingtalk.corp-id=testCorp",
                        "dingtalk.corp-secret=testSecret"
                )
                .run(context -> {
                    assertThat(context).hasSingleBean(DingTalkProperties.class);
                    DingTalkProperties props = context.getBean(DingTalkProperties.class);
                    assertThat(props.getCorpId()).isEqualTo("testCorp");
                    assertThat(props.getCorpSecret()).isEqualTo("testSecret");
                });
    }

    @Test
    void autoConfiguration_shouldNotOverrideCustomConfigProvider() {
        contextRunner
                .withBean("customConfigProvider", DingTalkConfigProvider.class,
                        () -> new DingTalkConfigProvider() {
                            @Override
                            public DingTalkProperties getDingTalkProperties(String corpId) { return null; }
                            @Override
                            public DingTalkCorpAppProperties getDingTalkCorpAppProperties(String corpId, String agentId) { return null; }
                            @Override
                            public DingTalkPersonalMiniAppProperties getDingTalkPersonalMiniAppProperties(String corpId, String appId) { return null; }
                            @Override
                            public DingTalkSuiteProperties getDingTalkSuiteProperties(String corpId, String suiteId) { return null; }
                            @Override
                            public DingTalkLoginProperties getDingTalkLoginProperties(String corpId, String appId) { return null; }
                            @Override
                            public DingTalkRobotProperties getDingTalkRobotProperties(String corpId, String robotId) { return null; }
                            @Override
                            public boolean hasAppKey(String appKey) { return false; }
                            @Override
                            public String getCorpId(String appKey) { return null; }
                            @Override
                            public String getCorpSecret(String corpId) { return null; }
                            @Override
                            public String getAppSecret(String corpId, String appKey) { return null; }
                        })
                .run(context -> {
                    assertThat(context).hasSingleBean(DingTalkConfigProvider.class);
                    assertThat(context).doesNotHaveBean(DefaultDingTalkConfigProvider.class);
                });
    }
}
