package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link MarkdownMessage}.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 */
class MarkdownMessageTest {

    @Test
    void defaultConstructor_shouldSetMessageType() {
        MarkdownMessage msg = new MarkdownMessage();
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.markdown);
    }

    @Test
    void constructorWithTitleAndText_shouldSetFields() {
        MarkdownMessage msg = new MarkdownMessage("title", "# Hello");
        assertThat(msg.getTitle()).isEqualTo("title");
        assertThat(msg.getText()).isEqualTo("# Hello");
    }

    @Test
    void constructorWithAtMobiles_shouldSetFields() {
        String[] mobiles = {"13800138000"};
        MarkdownMessage msg = new MarkdownMessage("title", "# Hello", mobiles);
        assertThat(msg.getAtMobiles()).isEqualTo(mobiles);
    }

    @Test
    void constructorWithAtAll_shouldSetFields() {
        MarkdownMessage msg = new MarkdownMessage("title", "# Hello", true);
        assertThat(msg.getIsAtAll()).isTrue();
    }

    @Test
    void settersAndGetters_shouldWork() {
        MarkdownMessage msg = new MarkdownMessage();
        msg.setTitle("t");
        msg.setText("tx");
        msg.setAtMobiles(new String[]{"123"});
        msg.setIsAtAll(true);
        assertThat(msg.getTitle()).isEqualTo("t");
        assertThat(msg.getText()).isEqualTo("tx");
        assertThat(msg.getAtMobiles()).containsExactly("123");
        assertThat(msg.getIsAtAll()).isTrue();
    }
}
