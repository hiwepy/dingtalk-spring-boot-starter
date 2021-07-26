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

import java.net.URI;
import java.util.Arrays;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGetJsapiTicketRequest;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiV2UserGetRequest;
import com.dingtalk.api.response.OapiGetJsapiTicketResponse;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.dingtalk.api.response.OapiV2UserGetResponse;
import com.dingtalk.spring.boot.bean.JsapiTicketSignature;
import com.dingtalk.spring.boot.bean.MessageType;
import com.dingtalk.spring.boot.property.DingTalkRobotProperties;
import com.dingtalk.spring.boot.utils.DingTalkUtils;
import com.dingtalk.spring.boot.utils.RandomUtils;
import com.taobao.api.ApiException;

/**
 */
public class DingTalkRobotOperations extends DingTalkOperations {

	public DingTalkRobotOperations(DingTalkTemplate template) {
		super(template);
	}
	
	protected String getWebhook(String robotId, Long timestamp) {
		DingTalkRobotProperties poperties = template.getRobotProperties(robotId);
        StringBuilder serverUrl = new StringBuilder(PREFIX + "/robot/send?access_token=").append(poperties.getAccessToken());
        String sign =  template.getSign(poperties.getSecretToken(), timestamp);
        serverUrl.append("&timestamp=").append(timestamp).append("&sign=").append(sign);
        return serverUrl.toString();
    }
	
	/**
     * 获取用户手机号
     *
     * @param userid
     * @return
     * @throws ApiException
     */
    public String getUserMobile(String access_token, String userid,  String lang) {
        try {
            DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/topapi/v2/user/get");
            OapiV2UserGetRequest req = new OapiV2UserGetRequest();
            req.setUserid(userid);
            req.setLang(lang);
            OapiV2UserGetResponse rsp = client.execute(req, access_token);
            System.out.println(rsp.getBody());
            return rsp.getResult().getMobile();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	 public void sendLinkMessage(String robotId, String mobile) {
	 
	 	Long timestamp = System.currentTimeMillis();
	 	
	    DingTalkClient client = new DefaultDingTalkClient(this.getWebhook(robotId, timestamp));
	    
        try {
            OapiRobotSendRequest request = new OapiRobotSendRequest();
            request.setMsgtype(MessageType.text.name());
            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
            text.setContent("我是机器人~");
            request.setText(text);
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(Arrays.asList(mobile));
            at.setIsAtAll(false);
            request.setAt(at);
            OapiRobotSendResponse response = client.execute(request);
            System.out.println(response.getBody());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

	 public void sendTextMessage(String robotId, String mobile) {
		 
		 	Long timestamp = System.currentTimeMillis();
		 	
		    DingTalkClient client = new DefaultDingTalkClient(this.getWebhook(robotId, timestamp));
		    
	        try {
	            OapiRobotSendRequest request = new OapiRobotSendRequest();
	            request.setMsgtype(MessageType.text.name());
	            OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
	            text.setContent("我是机器人~");
	            request.setText(text);
	            OapiRobotSendRequest.Actioncard cActioncard = new OapiRobotSendRequest.Actioncard();
	            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
	            at.setAtMobiles(Arrays.asList(mobile));
	            at.setIsAtAll(false);
	            request.setAt(at);
	            OapiRobotSendResponse response = client.execute(request);
	            System.out.println(response.getBody());
	        } catch (ApiException e) {
	            e.printStackTrace();
	        }
	    }

    

    /**
     * 发送WebHook消息到钉钉
     *
     * @param message
     * @return
     */
    public DingTalkResponse sendMessage(BaseMessage message) {
        return this.sendMessageByURL(getDefaultWebhook(), message);
    }

    /**
     * 发送文本消息到钉钉
     *
     * @param message
     * @return
     */
    public DingTalkResponse sendTextMessage(TextMessage message) {
        return this.sendMessage(message);
    }

    /**
     * 发送文本消息到钉钉
     *
     * @param content
     * @return
     */
    public DingTalkResponse sendTextMessage(String content) {
        return this.sendMessage(new TextMessage(content));
    }

    /**
     * 发送文本消息到钉钉
     *
     * @param content
     * @param atMobiles
     * @return
     */
    public DingTalkResponse sendTextMessage(String content, String[] atMobiles) {
        return this.sendMessage(new TextMessage(content, atMobiles));
    }

    /**
     * 发送文本消息到钉钉
     *
     * @param content
     * @param isAtAll
     * @return
     */
    public DingTalkResponse sendTextMessage(String content, boolean isAtAll) {
        return this.sendMessage(new TextMessage(content, isAtAll));
    }

    /**
     * 发送Link消息到钉钉
     *
     * @param message
     * @return
     */
    public DingTalkResponse sendLinkMessage(LinkMessage message) {
        return this.sendMessage(message);
    }

    /**
     * 发送Link消息到钉钉
     *
     * @param title
     * @param text
     * @param messageUrl
     * @return
     */
    public DingTalkResponse sendLinkMessage(String title, String text, String messageUrl) {
        return this.sendMessage(new LinkMessage(title, text, messageUrl));
    }

    /**
     * 发送Link消息到钉钉
     *
     * @param title
     * @param text
     * @param messageUrl
     * @param picUrl
     * @return
     */
    public DingTalkResponse sendLinkMessage(String title, String text, String messageUrl, String picUrl) {
        return this.sendMessage(new LinkMessage(title, text, messageUrl, picUrl));
    }

    /**
     * 发送MarkDown消息到钉钉
     *
     * @param message
     * @return
     */
    public DingTalkResponse sendMarkdownMessage(MarkdownMessage message) {
        return this.sendMessage(message);
    }

    /**
     * 发送MarkDown消息到钉钉
     *
     * @param title
     * @param text
     * @return
     */
    public DingTalkResponse sendMarkdownMessage(String title, String text) {
        return this.sendMessage(new MarkdownMessage(title, text));
    }

    /**
     * 发送MarkDown消息到钉钉
     *
     * @param title
     * @param text
     * @param atMobiles
     * @return
     */
    public DingTalkResponse sendMarkdownMessage(String title, String text, String[] atMobiles) {
        return this.sendMessage(new MarkdownMessage(title, text, atMobiles));
    }

    /**
     * 发送MarkDown消息到钉钉
     *
     * @param title
     * @param text
     * @param isAtAll
     * @return
     */
    public DingTalkResponse sendMarkdownMessage(String title, String text, boolean isAtAll) {
        return this.sendMessage(new MarkdownMessage(title, text, isAtAll));
    }

    /**
     * 发送ActionCard消息到钉钉
     *
     * @param message
     * @return
     */
    public DingTalkResponse sendActionCardMessage(ActionCardMessage message) {
        return this.sendMessage(message);
    }

    /**
     * 发送ActionCard消息到钉钉
     *
     * @param title
     * @param text
     * @return
     */
    public DingTalkResponse sendActionCardMessage(String title, String text) {
        return this.sendMessage(new ActionCardMessage(title, text));
    }

    /**
     * 发送ActionCard消息到钉钉
     *
     * @param title
     * @param text
     * @param hideAvatar
     * @return
     */
    public DingTalkResponse sendActionCardMessage(String title, String text, HideAvatarType hideAvatar) {
        return this.sendMessage(new ActionCardMessage(title, text, hideAvatar));
    }

    /**
     * 发送ActionCard消息到钉钉
     *
     * @param title
     * @param text
     * @param button
     * @return
     */
    public DingTalkResponse sendActionCardMessage(String title, String text, ActionCardButton button) {
        return this.sendMessage(new ActionCardMessage(title, text, button));
    }

    /**
     * 发送ActionCard消息到钉钉
     *
     * @param title
     * @param text
     * @param hideAvatar
     * @param button
     * @return
     */
    public DingTalkResponse sendActionCardMessage(String title, String text, HideAvatarType hideAvatar, ActionCardButton button) {
        return this.sendMessage(new ActionCardMessage(title, text, hideAvatar, button));
    }

    /**
     * 发送FeedCard消息到钉钉
     *
     * @param feedCardMessage
     * @return
     */
    public DingTalkResponse sendFeedCardMessage(FeedCardMessage feedCardMessage) {
        return this.sendMessage(feedCardMessage);
    }

    /**
     * 发送FeedCard消息到钉钉
     *
     * @param feedCardItems
     * @return
     */
    public DingTalkResponse sendFeedCardMessage(List<FeedCardMessageItem> feedCardItems) {
        return this.sendMessage(new FeedCardMessage(feedCardItems));
    }
 
}
