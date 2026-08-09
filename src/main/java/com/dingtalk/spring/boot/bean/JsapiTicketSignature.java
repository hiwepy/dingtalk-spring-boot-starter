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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the signature data required for DingTalk JSAPI calls, including agent ID,
 * URL, nonce string, timestamp, enterprise ID, and the computed signature.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JsapiTicketSignature implements Serializable {

	private static final long serialVersionUID = 7442708886169868689L;
	
	/** The application agent ID. */
	private String agentId;
	/** The current page URL, excluding the hash fragment. */
	private String url;
	/** A random nonce string. */
	private String nonceStr;
	/** The timestamp used for signature computation, must match between client and server. */
	private long timestamp;
	/** The enterprise ID. */
	private String corpId;
	/** The computed signature string. */
	private String signature;

}
