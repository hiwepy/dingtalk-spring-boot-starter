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
 * Represents a DingTalk plain text message with at-mention support.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class TextMessage extends BaseMessage {

	/** The text message content. */
	private String content;
	/** Mobile phone numbers of group members to @mention. */
	private String[] atMobiles;
	/** Whether to @mention all group members. */
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
