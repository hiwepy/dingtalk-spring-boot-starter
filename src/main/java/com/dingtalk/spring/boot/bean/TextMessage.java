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
/**
 * <p>Auto-configuration for TextMessage.</p>
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
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

	/** @return return the content. */
	public String getContent() {
		return content;
	}

	/** @param content set the content. */
	public void setContent(String content) {
		this.content = content;
	}

	/** @return return the at mobiles. */
	public String[] getAtMobiles() {
		return atMobiles;
	}

	/** @param atMobiles set the at mobiles. */
	public void setAtMobiles(String[] atMobiles) {
		this.atMobiles = atMobiles;
	}

	/** @return return whether at all is enabled. */
	public boolean isAtAll() {
		return atAll;
	}

	/** @param atAll set the at all. */
	public void setAtAll(boolean atAll) {
		this.atAll = atAll;
	}

}
