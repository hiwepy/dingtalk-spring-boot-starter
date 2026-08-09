package com.dingtalk.spring.boot.bean;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests for {@link FeedCardMessage}.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 */
class FeedCardMessageTest {

    @Test
    void defaultConstructor_shouldSetMessageType() {
        FeedCardMessage msg = new FeedCardMessage();
        assertThat(msg.getMsgtype()).isEqualTo(MessageType.feedCard);
        assertThat(msg.getFeedCardItems()).isEmpty();
    }

    @Test
    void constructorWithArrayList_shouldSetItems() {
        ArrayList<FeedCardMessageItem> items = new ArrayList<>();
        items.add(new FeedCardMessageItem("T1", "http://u1.com", "http://p1.com"));
        FeedCardMessage msg = new FeedCardMessage(items);
        assertThat(msg.getFeedCardItems()).hasSize(1);
    }

    @Test
    void constructorWithNonArrayList_shouldThrow() {
        List<FeedCardMessageItem> items = Collections.singletonList(
                new FeedCardMessageItem("T1", "http://u1.com", "http://p1.com"));
        assertThatThrownBy(() -> new FeedCardMessage(items))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructorWithTooManyItems_shouldThrow() {
        ArrayList<FeedCardMessageItem> items = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            items.add(new FeedCardMessageItem("T" + i, "http://u" + i + ".com", "http://p" + i + ".com"));
        }
        assertThatThrownBy(() -> new FeedCardMessage(items))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addFeedCardItem_shouldAddItem() {
        FeedCardMessage msg = new FeedCardMessage();
        msg.addFeedCardItem(new FeedCardMessageItem("T1", "http://u1.com", "http://p1.com"));
        assertThat(msg.getFeedCardItems()).hasSize(1);
    }

    @Test
    void addFeedCardItem_withNullItem_shouldThrow() {
        FeedCardMessage msg = new FeedCardMessage();
        assertThatThrownBy(() -> msg.addFeedCardItem(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addFeedCardItem_withEmptyTitle_shouldThrow() {
        FeedCardMessage msg = new FeedCardMessage();
        assertThatThrownBy(() -> msg.addFeedCardItem(new FeedCardMessageItem("", "http://u.com", "http://p.com")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addFeedCardItem_withEmptyUrl_shouldThrow() {
        FeedCardMessage msg = new FeedCardMessage();
        assertThatThrownBy(() -> msg.addFeedCardItem(new FeedCardMessageItem("T", "", "http://p.com")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addFeedCardItem_withEmptyPicUrl_shouldThrow() {
        FeedCardMessage msg = new FeedCardMessage();
        assertThatThrownBy(() -> msg.addFeedCardItem(new FeedCardMessageItem("T", "http://u.com", "")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
