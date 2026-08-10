package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link LinkMessage}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class LinkMessageTest {

    @Test
    void defaultConstructor_shouldSetMessageType() {
        LinkMessage msg = new LinkMessage();
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.link);
    }

    @Test
    void constructorWithTitleTextUrl_shouldSetFields() {
        LinkMessage msg = new LinkMessage("title", "text", "http://example.com");
        assertThat(msg.getTitle()).isEqualTo("title");
        assertThat(msg.getText()).isEqualTo("text");
        assertThat(msg.getMessageUrl()).isEqualTo("http://example.com");
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.link);
    }

    @Test
    void constructorWithTitleTextUrlPic_shouldSetAllFields() {
        LinkMessage msg = new LinkMessage("title", "text", "http://example.com", "http://pic.com/img.png");
        assertThat(msg.getTitle()).isEqualTo("title");
        assertThat(msg.getText()).isEqualTo("text");
        assertThat(msg.getMessageUrl()).isEqualTo("http://example.com");
        assertThat(msg.getPicUrl()).isEqualTo("http://pic.com/img.png");
    }

    @Test
    void settersAndGetters_shouldWork() {
        LinkMessage msg = new LinkMessage();
        msg.setTitle("t");
        msg.setText("tx");
        msg.setMessageUrl("http://url.com");
        msg.setPicUrl("http://pic.com");
        assertThat(msg.getTitle()).isEqualTo("t");
        assertThat(msg.getText()).isEqualTo("tx");
        assertThat(msg.getMessageUrl()).isEqualTo("http://url.com");
        assertThat(msg.getPicUrl()).isEqualTo("http://pic.com");
    }
}
