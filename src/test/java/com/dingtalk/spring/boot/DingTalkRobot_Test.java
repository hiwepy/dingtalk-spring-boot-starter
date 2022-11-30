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
package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.bean.LinkMessage;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * TODO
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootApplication
@Slf4j
public class DingTalkRobot_Test {

	private String corpId = "ding5c936b989299e59435c2f4657eb6378f";
	private String robotId = "13849004";
	@Autowired
	private DingTalkTemplate template;

	/**
	 * 发送工作通知
	 */
	@Test
	public void sendLinkMessage() {

		try {

			template.opsForRobot().sendLinkMessage(corpId, robotId, "LinkMessage", "Link Message Test.", "https://open-dev.dingtalk.com/v1#/");

			template.opsForRobot().sendLinkMessage(corpId, robotId, "LinkMessage", "Link Message Test.",
					"https://open-dev.dingtalk.com/v1#/", "https://img.alicdn.com/imgextra/i3/O1CN01M5RDKH1lCfAYcBzOd_!!6000000004783-2-tps-288-194.png");

			LinkMessage message = new LinkMessage();
			message.setTitle("LinkMessage");
			message.setText("Link Message Test.");
			message.setMessageUrl("https://open-dev.dingtalk.com/v1#/");
			message.setPicUrl("https://img.alicdn.com/imgextra/i3/O1CN01M5RDKH1lCfAYcBzOd_!!6000000004783-2-tps-288-194.png");

			template.opsForRobot().sendLinkMessage(corpId, robotId, message);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
