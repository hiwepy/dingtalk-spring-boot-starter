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
 * Abstract base class for all DingTalk message types.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@SuppressWarnings("serial")
/**
 * <p>Auto-configuration for BaseMessage.</p>
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public abstract class BaseMessage implements Serializable {

    /** The message type. */
    protected MessageType msgtype;

	public BaseMessage(MessageType msgtype) {
		super();
		this.msgtype = msgtype;
	}

	/** @return return the msgtype. */
	public MessageType getMsgtype() {
		return msgtype;
	}

	/** @param msgtype set the msgtype. */
	public void setMsgtype(MessageType msgtype) {
		this.msgtype = msgtype;
	}
	
}
