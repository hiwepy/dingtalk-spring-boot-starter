/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.dingtalk.spring.boot.bean;


import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

/**
 * 消息卡片类型Message
 */
@SuppressWarnings("serial")
public class FeedCardMessage extends BaseMessage {

    private static final int MAX_BUTTON_COUNT = 10;
    private static final int MIN_BUTTON_COUNT = 1;

    /**
     * 消息明细条目
     */
    private List<FeedCardMessageItem> feedCardItems = new ArrayList<>();

    public FeedCardMessage() {
        super(MessageType.feedCard);
    }

    public FeedCardMessage(List<FeedCardMessageItem> feedCardItems) {
        super(MessageType.feedCard);
        if (!(feedCardItems instanceof ArrayList)) {
            throw new IllegalArgumentException("feedCardItems must bu ArrayList");
        }
        if (feedCardItems.size() > MAX_BUTTON_COUNT) {
            throw new IllegalArgumentException("the number of buttons is not advise bigger than " + MAX_BUTTON_COUNT);
        }
        this.feedCardItems = feedCardItems;
    }

    public List<FeedCardMessageItem> getFeedCardItems() {
        return feedCardItems;
    }

    public void addFeedCardItem(FeedCardMessageItem item) {
        if (item == null || StringUtils.isEmpty(item.getMessageURL()) ||
                StringUtils.isEmpty(item.getPicURL()) || StringUtils.isEmpty(item.getTitle())) {
            throw new IllegalArgumentException("please check the necessary parameters of item!");
        }
        if (feedCardItems == null || feedCardItems.size() >= MAX_BUTTON_COUNT) {
            throw new IllegalArgumentException("the number of buttons is not advise bigger than " + MAX_BUTTON_COUNT);
        }
        feedCardItems.add(item);
    }
}
