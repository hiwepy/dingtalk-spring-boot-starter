package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link TextMessage}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class TextMessageTest {

    @Test
    void defaultConstructor_shouldSetMessageType() {
        TextMessage msg = new TextMessage();
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.text);
    }

    @Test
    void constructorWithContent_shouldSetContent() {
        TextMessage msg = new TextMessage("hello");
        assertThat(msg.getContent()).isEqualTo("hello");
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.text);
    }

    @Test
    void constructorWithContentAndAtMobiles_shouldSetFields() {
        String[] mobiles = {"13800138000", "13900139000"};
        TextMessage msg = new TextMessage("hello", mobiles);
        assertThat(msg.getContent()).isEqualTo("hello");
        assertThat(msg.getAtMobiles()).isEqualTo(mobiles);
    }

    @Test
    void constructorWithContentAndAtAll_shouldSetFields() {
        TextMessage msg = new TextMessage("hello", true);
        assertThat(msg.getContent()).isEqualTo("hello");
        assertThat(msg.isAtAll()).isTrue();
    }

    @Test
    void settersAndGetters_shouldWork() {
        TextMessage msg = new TextMessage();
        msg.setContent("test");
        msg.setAtMobiles(new String[]{"123"});
        msg.setAtAll(true);
        assertThat(msg.getContent()).isEqualTo("test");
        assertThat(msg.getAtMobiles()).containsExactly("123");
        assertThat(msg.isAtAll()).isTrue();
    }
}
