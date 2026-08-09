package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link ActionCardMessage}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class ActionCardMessageTest {

    @Test
    void defaultConstructor_shouldSetMessageType() {
        ActionCardMessage msg = new ActionCardMessage();
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.actionCard);
        assertThat(msg.getHideAvatar()).isEqualTo(HideAvatarType.UNHIDE);
        assertThat(msg.getBtnOrientation()).isEqualTo(ButtonOrientationType.HORIZONTAL);
        assertThat(msg.getButtons()).isEmpty();
    }

    @Test
    void constructorWithTitleAndText_shouldSetFields() {
        ActionCardMessage msg = new ActionCardMessage("title", "text");
        assertThat(msg.getTitle()).isEqualTo("title");
        assertThat(msg.getText()).isEqualTo("text");
    }

    @Test
    void constructorWithHideAvatar_shouldSetFields() {
        ActionCardMessage msg = new ActionCardMessage("title", "text", HideAvatarType.HIDE);
        assertThat(msg.getHideAvatar()).isEqualTo(HideAvatarType.HIDE);
    }

    @Test
    void constructorWithButton_shouldAddButton() {
        ActionCardButton button = new ActionCardButton("Read", "http://example.com");
        ActionCardMessage msg = new ActionCardMessage("title", "text", button);
        assertThat(msg.getButtons()).hasSize(1);
        assertThat(msg.getButtons().get(0).getTitle()).isEqualTo("Read");
    }

    @Test
    void constructorWithHideAvatarAndButton_shouldSetFields() {
        ActionCardButton button = new ActionCardButton("Go", "http://example.com");
        ActionCardMessage msg = new ActionCardMessage("title", "text", HideAvatarType.HIDE, button);
        assertThat(msg.getHideAvatar()).isEqualTo(HideAvatarType.HIDE);
        assertThat(msg.getButtons()).hasSize(1);
    }

    @Test
    void addButton_shouldAddButton() {
        ActionCardMessage msg = new ActionCardMessage();
        msg.addButton(new ActionCardButton("B1", "http://b1.com"));
        assertThat(msg.getButtons()).hasSize(1);
    }

    @Test
    void addButton_withNull_shouldThrow() {
        ActionCardMessage msg = new ActionCardMessage();
        assertThatThrownBy(() -> msg.addButton(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addButton_exceedMax_shouldThrow() {
        ActionCardMessage msg = new ActionCardMessage();
        for (int i = 0; i < 5; i++) {
            msg.addButton(new ActionCardButton("B" + i, "http://b" + i + ".com"));
        }
        assertThatThrownBy(() -> msg.addButton(new ActionCardButton("B6", "http://b6.com")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void settersAndGetters_shouldWork() {
        ActionCardMessage msg = new ActionCardMessage();
        msg.setTitle("t");
        msg.setText("tx");
        msg.setHideAvatar(HideAvatarType.HIDE);
        msg.setBtnOrientation(ButtonOrientationType.VERTICAL);
        msg.setButtonView(true);
        assertThat(msg.getTitle()).isEqualTo("t");
        assertThat(msg.getText()).isEqualTo("tx");
        assertThat(msg.getHideAvatar()).isEqualTo(HideAvatarType.HIDE);
        assertThat(msg.getBtnOrientation()).isEqualTo(ButtonOrientationType.VERTICAL);
        assertThat(msg.isButtonView()).isTrue();
    }
}
