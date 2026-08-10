package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MessageType}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class MessageTypeTest {

    @Test
    void values_shouldContainAllTypes() {
        assertThat(MessageType.values()).containsExactly(
                MessageType.text, MessageType.link, MessageType.markdown,
                MessageType.actionCard, MessageType.feedCard);
    }

    @Test
    void valueOf_shouldReturnCorrectType() {
        assertThat(MessageType.valueOf("text")).isEqualTo(MessageType.text);
        assertThat(MessageType.valueOf("link")).isEqualTo(MessageType.link);
        assertThat(MessageType.valueOf("markdown")).isEqualTo(MessageType.markdown);
        assertThat(MessageType.valueOf("actionCard")).isEqualTo(MessageType.actionCard);
        assertThat(MessageType.valueOf("feedCard")).isEqualTo(MessageType.feedCard);
    }
}
