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

	public TextMessage() {
		super(MessageType.text);
	}

	public TextMessage(String content) {
		super(MessageType.text);
		this.content = content;
	}

	public TextMessage(String content, boolean isAtAll) {
		super(MessageType.text);
		super.setIsAtAll(isAtAll);
		this.content = content;
	}

	public TextMessage(String content, String[] atMobiles) {
		super(MessageType.text);
		super.setAtMobiles(atMobiles);
		this.content = content;
	}

	public TextMessage(String content, String[] atMobiles, String[] atUserIds) {
		super(MessageType.text);
		super.setAtMobiles(atMobiles);
		super.setAtUserIds(atUserIds);
		this.content = content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
