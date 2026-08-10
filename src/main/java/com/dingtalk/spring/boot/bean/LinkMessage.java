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
 * Represents a DingTalk link message with title, text, image, and target URL.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class LinkMessage extends BaseMessage {

    /** The message body text. */
    private String text;
    /** The message title. */
    private String title;
    /** The cover image URL. */
    private String picUrl;
    /** The URL to navigate to when the message is clicked. */
    private String messageUrl;

    public LinkMessage() {
    	super(MessageType.link);
    }

    public LinkMessage(String title, String text, String messageUrl) {
    	super(MessageType.link);
        this.text = text;
        this.title = title;
        this.messageUrl = messageUrl;
    }

    public LinkMessage(String title, String text, String messageUrl, String picUrl) {
    	super(MessageType.link);
        this.text = text;
        this.title = title;
        this.picUrl = picUrl;
        this.messageUrl = messageUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getMessageUrl() {
        return messageUrl;
    }

    public void setMessageUrl(String messageUrl) {
        this.messageUrl = messageUrl;
    }
    
}