package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link BaseMessage}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class BaseMessageTest {

    @Test
    void textMessage_shouldHaveCorrectType() {
        TextMessage msg = new TextMessage();
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.text);
    }

    @Test
    void linkMessage_shouldHaveCorrectType() {
        LinkMessage msg = new LinkMessage();
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.link);
    }

    @Test
    void markdownMessage_shouldHaveCorrectType() {
        MarkdownMessage msg = new MarkdownMessage();
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.markdown);
    }

    @Test
    void actionCardMessage_shouldHaveCorrectType() {
        ActionCardMessage msg = new ActionCardMessage();
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.actionCard);
    }

    @Test
    void feedCardMessage_shouldHaveCorrectType() {
        FeedCardMessage msg = new FeedCardMessage();
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.feedCard);
    }

    @Test
    void setMsgtype_shouldUpdateType() {
        TextMessage msg = new TextMessage();
        msg.setMsgtype(MessageType.markdown);
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.markdown);
    }
}
