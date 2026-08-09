package com.dingtalk.spring.boot;

import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.spring.boot.bean.*;
import com.dingtalk.spring.boot.property.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link DingTalkRobotOperations}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class DingTalkRobotOperationsTest {

    private DingTalkRobotOperations robotOps;

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
        DingTalkTemplate template = new DingTalkTemplate(configProvider, tokenProvider);
        robotOps = new DingTalkRobotOperations(template);
    }

    @Test
    void buidRequest_withTextMessage_shouldSetCorrectType() {
        TextMessage msg = new TextMessage("hello");
        OapiRobotSendRequest request = robotOps.buidRequest(msg);
        assertThat(request.getMsgtype()).isEqualTo("text");
    }

    @Test
    void buidRequest_withTextMessageAndAtMobiles_shouldSetCorrectType() {
        TextMessage msg = new TextMessage("hello", new String[]{"13800138000"});
        OapiRobotSendRequest request = robotOps.buidRequest(msg);
        assertThat(request.getMsgtype()).isEqualTo("text");
    }

    @Test
    void buidRequest_withTextMessageAndAtAll_shouldSetCorrectType() {
        TextMessage msg = new TextMessage("hello", true);
        OapiRobotSendRequest request = robotOps.buidRequest(msg);
        assertThat(request.getMsgtype()).isEqualTo("text");
    }

    @Test
    void buidRequest_withLinkMessage_shouldSetCorrectType() {
        LinkMessage msg = new LinkMessage("title", "text", "http://url.com");
        OapiRobotSendRequest request = robotOps.buidRequest(msg);
        assertThat(request.getMsgtype()).isEqualTo("link");
    }

    @Test
    void buidRequest_withMarkdownMessage_shouldSetCorrectType() {
        MarkdownMessage msg = new MarkdownMessage("title", "# Hello");
        OapiRobotSendRequest request = robotOps.buidRequest(msg);
        assertThat(request.getMsgtype()).isEqualTo("markdown");
    }

    @Test
    void buidRequest_withActionCardMessage_shouldSetCorrectType() {
        ActionCardMessage msg = new ActionCardMessage("title", "text");
        OapiRobotSendRequest request = robotOps.buidRequest(msg);
        assertThat(request.getMsgtype()).isEqualTo("actionCard");
    }

    @Test
    void buidRequest_withFeedCardMessage_shouldSetCorrectType() {
        FeedCardMessage msg = new FeedCardMessage();
        OapiRobotSendRequest request = robotOps.buidRequest(msg);
        assertThat(request.getMsgtype()).isEqualTo("feedCard");
    }

    @Test
    void getWebhook_shouldReturnUrlWithTokenAndSign() {
        String webhook = robotOps.getWebhook("corpId", "r1", 1234567890L);
        assertThat(webhook).contains("https://oapi.dingtalk.com/robot/send");
        assertThat(webhook).contains("access_token=token1");
        assertThat(webhook).contains("timestamp=1234567890");
        assertThat(webhook).contains("sign=");
    }
}
