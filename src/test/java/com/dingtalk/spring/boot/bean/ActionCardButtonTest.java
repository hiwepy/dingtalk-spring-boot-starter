package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link ActionCardButton}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class ActionCardButtonTest {

    @Test
    void defaultConstructor_shouldCreateEmptyButton() {
        ActionCardButton button = new ActionCardButton();
        assertThat(button.getTitle()).isNull();
        assertThat(button.getActionURL()).isNull();
    }

    @Test
    void constructorWithTitleAndUrl_shouldSetFields() {
        ActionCardButton button = new ActionCardButton("Read", "http://example.com");
        assertThat(button.getTitle()).isEqualTo("Read");
        assertThat(button.getActionURL()).isEqualTo("http://example.com");
    }

    @Test
    void defaultReadButton_shouldCreateButtonWithDefaultTitle() {
        ActionCardButton button = ActionCardButton.defaultReadButton("http://example.com");
        assertThat(button.getTitle()).isEqualTo("阅读全文");
        assertThat(button.getActionURL()).isEqualTo("http://example.com");
    }

    @Test
    void settersAndGetters_shouldWork() {
        ActionCardButton button = new ActionCardButton();
        button.setTitle("Go");
        button.setActionURL("http://go.com");
        assertThat(button.getTitle()).isEqualTo("Go");
        assertThat(button.getActionURL()).isEqualTo("http://go.com");
    }
}
