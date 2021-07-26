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
 * 文本请求消息
 */
@SuppressWarnings("serial")
public class MarkdownMessage extends BaseMessage {

	/**
     * 消息简介
     */
    private String text;

    /**
     * 消息标题
     */
    private String title;

    /**
     * 可以通过群成员的绑定手机号来艾特具体的群成员
     */
    private String[] atMobiles;

    /**
     * 是否艾特所有人
     * 也可以设置isAtAll=true来艾特所有人
     */
    private boolean isAtAll;

	public MarkdownMessage() {
		super(MessageType.markdown);
	}

	public MarkdownMessage(String title, String text) {
		super(MessageType.markdown);
		this.text = text;
		this.title = title;
	}

	public MarkdownMessage(String title, String text, String[] atMobiles) {
		super(MessageType.markdown);
		this.text = text;
		this.title = title;
		this.atMobiles = atMobiles;
	}

	public MarkdownMessage(String title, String text, boolean isAtAll) {
		super(MessageType.markdown);
		this.text = text;
		this.title = title;
		this.isAtAll = isAtAll;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String[] getAtMobiles() {
		return atMobiles;
	}

	public void setAtMobiles(String[] atMobiles) {
		this.atMobiles = atMobiles;
	}

	public boolean getIsAtAll() {
		return isAtAll;
	}

	public void setIsAtAll(boolean isAtAll) {
		this.isAtAll = isAtAll;
	}

}
