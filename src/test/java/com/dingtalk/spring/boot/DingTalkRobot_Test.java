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

import java.net.URLEncoder;
import java.util.Base64;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.taobao.api.ApiException;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 * @author 		： <a href="https://github.com/hiwepy">hiwepy</a>
 */
@Slf4j
public class DingTalkRobot_Test {

	/**
     * 计算签名
     * 参考：https://ding-doc.dingtalk.com/doc#/serverapi2/qf2nxq/9e91d73c
     *
     * @param secret    密钥，机器人安全设置页面，加签一栏下面显示的SEC开头的字符
     * @param timestamp 当前时间戳，毫秒级单位
     * @return 根据时间戳计算后的签名信息
     */
    protected static String getSign(String secret, Long timestamp) {
        try {
            String stringToSign = timestamp + "\n" + secret;
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
            byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
            String sign = URLEncoder.encode(new String(Base64.getEncoder().encode(signData)), "UTF-8");
            log.debug("【发送钉钉群消息】获取到签名sign = {}", sign);
            return sign;
        } catch (Exception e) {
            log.error("【发送钉钉群消息】计算签名异常，errMsg = {}", e);
            return null;
        }
    }
    
    public static void main(String[] args) {
		
    	StringBuilder serverUrl = new StringBuilder("");
        Long timestamp = System.currentTimeMillis();
        String sign =  getSign("", timestamp);
        serverUrl.append("&timestamp=").append(timestamp).append("&sign=").append(sign);
         
    	 DefaultDingTalkClient client = new DefaultDingTalkClient(serverUrl.toString());
    	 try {
             OapiRobotSendRequest request = new OapiRobotSendRequest();
             request.setMsgtype("text");
            // request.setTimestamp(timestamp);
             OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
             text.setContent("我是机器人~");
             request.setText(text);
             //OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
             //at.setAtMobiles(Arrays.asList(mobile));
            // at.setIsAtAll(false);
             //request.setAt(at);
             OapiRobotSendResponse response = client.execute(request);
             System.out.println(response.getBody());
         } catch (ApiException e) {
             e.printStackTrace();
         }
    	 /**
    	 OapiRobotSendRequest request = new OapiRobotSendRequest();
    	 request.setMsgtype("text");
    	 OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
    	 text.setContent("测试文本消息");
    	 request.setText(text);
    	 OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
    	 at.setAtMobiles(Arrays.asList("132xxxxxxxx"));
    	 // isAtAll类型如果不为Boolean，请升级至最新SDK
    	 at.setIsAtAll(true);
    	 at.setAtMobiles(Arrays.asList("109929","32099"));
    	 request.setAt(at);

    	 request.setMsgtype("link");
    	 OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
    	 link.setMessageUrl("https://www.dingtalk.com/");
    	 link.setPicUrl("");
    	 link.setTitle("时代的火车向前开");
    	 link.setText("这个即将发布的新版本，创始人xx称它为红树林。而在此之前，每当面临重大升级，产品经理们都会取一个应景的代号，这一次，为什么是红树林");
    	 request.setLink(link);

    	 request.setMsgtype("markdown");
    	 OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
    	 markdown.setTitle("杭州天气");
    	 markdown.setText("#### 杭州天气 @156xxxx8827\n" +
    	         "> 9度，西北风1级，空气良89，相对温度73%\n\n" +
    	         "> ![screenshot](https://gw.alicdn.com/tfs/TB1ut3xxbsrBKNjSZFpXXcXhFXa-846-786.png)\n"  +
    	         "> ###### 10点20分发布 [天气](http://www.thinkpage.cn/) \n");
    	 request.setMarkdown(markdown);
    	 OapiRobotSendResponse response = client.execute(request);*/
    	 
	}
    
    
}
