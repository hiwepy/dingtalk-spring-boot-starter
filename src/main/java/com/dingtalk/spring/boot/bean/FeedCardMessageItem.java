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

/**
 * Represents an individual item in a DingTalk feed card message.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class FeedCardMessageItem {

    /** The item title. */
    private String title;
    /** The URL to navigate to when the item is clicked. */
    private String messageURL;
    /** The cover image URL for the item. */
    private String picURL;

    public FeedCardMessageItem() {
    }

    public FeedCardMessageItem(String title, String messageURL, String picURL) {
        this.title = title;
        this.messageURL = messageURL;
        this.picURL = picURL;
    }

    /** @return return the title. */
    public String getTitle() {
        return title;
    }

    /** @param title set the title. */
    public void setTitle(String title) {
        this.title = title;
    }

    /** @return return the message u r l. */
    public String getMessageURL() {
        return messageURL;
    }

    /** @param messageURL set the message u r l. */
    public void setMessageURL(String messageURL) {
        this.messageURL = messageURL;
    }

    /** @return return the pic u r l. */
    public String getPicURL() {
        return picURL;
    }

    /** @param picURL set the pic u r l. */
    public void setPicURL(String picURL) {
        this.picURL = picURL;
    }
}