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
package com.dingtalk.spring.boot.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

import com.taobao.api.ApiException;

/**
 * Utility class for DingTalk JSAPI signature computation using SHA-1 hashing.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 * @see <a href="https://ding-doc.dingtalk.com/doc#/dev/uwa7vs">JSAPI Signature Documentation</a>
 */
public class DingTalkUtils {

	/**
	 * Computes the SHA-1 signature for a DingTalk JSAPI call.
	 *
	 * @param jsapiTicket the JSAPI ticket
	 * @param nonceStr    a random nonce string
	 * @param timeStamp   the current timestamp in seconds
	 * @param url         the current page URL
	 * @return the hex-encoded SHA-1 signature
	 * @throws ApiException if the signing algorithm is unavailable or encoding fails
	 */
	public static String sign(String jsapiTicket, String nonceStr, long timeStamp, String url) throws ApiException {
		String plain = "jsapi_ticket=" + jsapiTicket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
				+ "&url=" + url;
		try {
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.reset();
			sha1.update(plain.getBytes("UTF-8"));
			return byteToHex(sha1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new ApiException(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			throw new ApiException(e.getMessage());
		}
	}

	// 字节数组转化成十六进制字符串
	private static String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}

}
