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
 * Markdown消息
 */
@SuppressWarnings("serial")
public class TextMessage extends BaseMessage {

	/**
	 * 文本消息的具体内容
	 */
	private String content;

	/**
	 * 可以通过群成员的绑定手机号来艾特具体的群成员
	 */
	private String[] atMobiles;

	/**
	 * 是否艾特所有人 也可以设置atAll=true来艾特所有人
	 */
	private boolean atAll;

	public TextMessage() {
		super(MessageType.text);
	}

	public TextMessage(String content) {
		super(MessageType.text);
		this.content = content;
	}

	public TextMessage(String content, String[] atMobiles) {
		super(MessageType.text);
		this.content = content;
		this.atMobiles = atMobiles;
	}

	public TextMessage(String content, boolean atAll) {
		super(MessageType.text);
		this.content = content;
		this.atAll = atAll;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String[] getAtMobiles() {
		return atMobiles;
	}

	public void setAtMobiles(String[] atMobiles) {
		this.atMobiles = atMobiles;
	}

	public boolean isAtAll() {
		return atAll;
	}

	public void setAtAll(boolean atAll) {
		this.atAll = atAll;
	}

}
