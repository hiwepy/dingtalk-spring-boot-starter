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

import java.io.Serializable;

/**
 * 请求消息的抽象类
 */
@SuppressWarnings("serial")
public abstract class BaseMessage implements Serializable {

    /**
     * 消息类型
     */
    protected MessageType msgtype;
	/**
	 * 被@人的手机号
	 */
	private String[] atMobiles;
	/**
	 * 被@人的工号
	 */
	private String[] atUserIds;
	/**
	 * @所有人时:true,否则为:false
	 */
	private boolean isAtAll;

	public BaseMessage(MessageType msgtype) {
		super();
		this.msgtype = msgtype;
	}

	public MessageType getMsgtype() {
		return msgtype;
	}

	public void setAtMobiles(String[] atMobiles) {
		this.atMobiles = atMobiles;
	}

	public String[] getAtMobiles() {
		return atMobiles;
	}

	public void setAtUserIds(String[] atUserIds) {
		this.atUserIds = atUserIds;
	}

	public String[] getAtUserIds() {
		return atUserIds;
	}

	public void setIsAtAll(boolean atAll) {
		this.isAtAll = atAll;
	}

	public boolean isAtAll() {
		return isAtAll;
	}

}
