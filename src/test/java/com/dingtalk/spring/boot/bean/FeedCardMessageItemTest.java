package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link FeedCardMessageItem}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class FeedCardMessageItemTest {

    @Test
    void defaultConstructor_shouldCreateEmptyItem() {
        FeedCardMessageItem item = new FeedCardMessageItem();
        assertThat(item.getTitle()).isNull();
        assertThat(item.getMessageURL()).isNull();
        assertThat(item.getPicURL()).isNull();
    }

    @Test
    void constructorWithAllFields_shouldSetFields() {
        FeedCardMessageItem item = new FeedCardMessageItem("Title", "http://msg.com", "http://pic.com");
        assertThat(item.getTitle()).isEqualTo("Title");
        assertThat(item.getMessageURL()).isEqualTo("http://msg.com");
        assertThat(item.getPicURL()).isEqualTo("http://pic.com");
    }

    @Test
    void settersAndGetters_shouldWork() {
        FeedCardMessageItem item = new FeedCardMessageItem();
        item.setTitle("T");
        item.setMessageURL("http://m.com");
        item.setPicURL("http://p.com");
        assertThat(item.getTitle()).isEqualTo("T");
        assertThat(item.getMessageURL()).isEqualTo("http://m.com");
        assertThat(item.getPicURL()).isEqualTo("http://p.com");
    }
}
