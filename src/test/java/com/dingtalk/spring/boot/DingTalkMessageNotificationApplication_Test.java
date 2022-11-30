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

import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.spring.boot.bean.LinkMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DingTalkMessageNotificationApplication_Test {

	private String corpId = "ding5c936b989299e59435c2f4657eb6378f";
	private String robotId = "13849004";
	@Autowired
	private DingTalkTemplate template;

	/**
	 * 发送工作通知
	 */
	@Test
	public void testSendSms() {

		try {
			template.opsForMsgNotification().sendWorkNotice(corpId, robotId, (request -> {

				OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
				OapiMessageCorpconversationAsyncsendV2Request.OA oa = new OapiMessageCorpconversationAsyncsendV2Request.OA();


				OapiMessageCorpconversationAsyncsendV2Request.Head head = new OapiMessageCorpconversationAsyncsendV2Request.Head();
				head.setBgcolor("FFFF6A00");
				head.setText("测试");
				OapiMessageCorpconversationAsyncsendV2Request.Body body = new OapiMessageCorpconversationAsyncsendV2Request.Body();
				List<OapiMessageCorpconversationAsyncsendV2Request.Form> list = new ArrayList<>();
				OapiMessageCorpconversationAsyncsendV2Request.Form form1 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
				form1.setKey("姓名:");
				form1.setValue("小钉");
				OapiMessageCorpconversationAsyncsendV2Request.Form form2 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
				form2.setKey("年龄:");
				form2.setValue("21");
				OapiMessageCorpconversationAsyncsendV2Request.Form form3 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
				form3.setKey("身高:");
				form3.setValue("1.8米");
				OapiMessageCorpconversationAsyncsendV2Request.Form form4 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
				form4.setKey("体重:");
				form4.setValue("130斤");
				OapiMessageCorpconversationAsyncsendV2Request.Form form5 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
				form5.setKey("学历:");
				form5.setValue("本科");
				OapiMessageCorpconversationAsyncsendV2Request.Form form6 = new OapiMessageCorpconversationAsyncsendV2Request.Form();
				form6.setKey("爱好:");
				form6.setValue("打球、听音乐");

				OapiMessageCorpconversationAsyncsendV2Request.Rich rich = new OapiMessageCorpconversationAsyncsendV2Request.Rich();
				rich.setNum("10.6");
				rich.setUnit("元");

				list.add(form1);
				list.add(form2);
				list.add(form3);
				list.add(form4);
				list.add(form5);
				list.add(form6);



				body.setForm(list);
				body.setRich(rich);
				body.setTitle("正文标题5");
				body.setImage("@lADOADmaWMzazQKA");
				body.setFileCount("3");
				body.setAuthor("小钉");


				OapiMessageCorpconversationAsyncsendV2Request.StatusBar statusBar = new OapiMessageCorpconversationAsyncsendV2Request.StatusBar();
				statusBar.setStatusBg("0xFFFF6A00");
				statusBar.setStatusValue("进行中");

				oa.setMessageUrl("https://dingtalk.com");
				oa.setHead(head);
				oa.setBody(body);
				oa.setStatusBar(statusBar);
				msg.setOa(oa);
				msg.setMsgtype("oa");
				msg.setOa(oa);
				request.setMsg(msg);

			}));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(DingTalkMessageNotificationApplication_Test.class, args);
	}
    
}
