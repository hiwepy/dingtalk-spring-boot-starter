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

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.dingtalk.spring.boot.bean.ActionCardButton;
import com.dingtalk.spring.boot.bean.ActionCardMessage;
import com.dingtalk.spring.boot.bean.BaseMessage;
import com.dingtalk.spring.boot.bean.FeedCardMessage;
import com.dingtalk.spring.boot.bean.FeedCardMessageItem;
import com.dingtalk.spring.boot.bean.HideAvatarType;
import com.dingtalk.spring.boot.bean.LinkMessage;
import com.dingtalk.spring.boot.bean.MarkdownMessage;
import com.dingtalk.spring.boot.bean.TextMessage;
import com.dingtalk.spring.boot.property.DingTalkRobotProperties;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.util.CollectionUtils;

/**
 */
public class DingTalkRobotOperations extends DingTalkOperations {

	public DingTalkRobotOperations(DingTalkTemplate template) {
		super(template);
	}

	protected String getWebhook(String corpId, String robotId, Long timestamp) throws ApiException {
		Optional<DingTalkRobotProperties> poperties = template.getDingTalkConfigProvider().getDingTalkRobotProperties(corpId, robotId);
        if(!poperties.isPresent()){
            throw new ApiException(String.format("未找到{0}，{1}想匹配的机器人配置。", corpId, robotId));
        }
        StringBuilder serverUrl = new StringBuilder(PREFIX + "/robot/send?access_token=").append(poperties.get().getAccessToken());
        String sign =  template.getSign(poperties.get().getSecretToken(), timestamp);
        serverUrl.append("&timestamp=").append(timestamp).append("&sign=").append(sign);
        return serverUrl.toString();
    }

    OapiRobotSendRequest buidRequest(BaseMessage message){

		OapiRobotSendRequest request = new OapiRobotSendRequest();
		request.setMsgtype(message.getMsgtype().name());

		switch (message.getMsgtype()) {
			case actionCard:{
                ActionCardMessage msg = (ActionCardMessage) message;
                OapiRobotSendRequest.Actioncard actioncard = new OapiRobotSendRequest.Actioncard();
                actioncard.setTitle(msg.getTitle());
                actioncard.setText(msg.getText());
                actioncard.setHideAvatar(msg.getHideAvatar().getValue());
                if(!CollectionUtils.isEmpty(msg.getButtons())){
                    if(msg.getButtons().size() == 1){
                        actioncard.setSingleTitle(msg.getButtons().get(0).getTitle());
                        actioncard.setSingleURL(msg.getButtons().get(0).getActionURL());
                    } else {
                        actioncard.setBtnOrientation(msg.getBtnOrientation().getValue());
                        actioncard.setBtns(msg.getButtons().stream().map(item -> {
                            OapiRobotSendRequest.Btns btns = new OapiRobotSendRequest.Btns();
                            btns.setTitle(item.getTitle());
                            btns.setActionURL(item.getActionURL());
                            return btns;
                        }).collect(Collectors.toList()));
                    }
                }
                request.setActionCard(actioncard);
			};break;
			case feedCard:{

                FeedCardMessage msg = (FeedCardMessage) message;
                OapiRobotSendRequest.Feedcard feedcard = new OapiRobotSendRequest.Feedcard();
                feedcard.setLinks(msg.getFeedCardItems().stream().map(item -> {
                    OapiRobotSendRequest.Links links = new OapiRobotSendRequest.Links();
                    links.setTitle(item.getTitle());
                    links.setMessageURL(item.getMessageURL());
                    links.setPicURL(item.getPicURL());
                    return links;
                }).collect(Collectors.toList()));
                request.setFeedCard(feedcard);

			};break;
			case link:{

                LinkMessage msg = (LinkMessage) message;
                OapiRobotSendRequest.Link link = new OapiRobotSendRequest.Link();
                link.setTitle(msg.getTitle());
                link.setText(msg.getText());
                link.setMessageUrl(msg.getMessageUrl());
                link.setPicUrl(msg.getPicUrl());
                request.setLink(link);

			};break;
			case markdown:{

                MarkdownMessage msg = (MarkdownMessage) message;
                OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();
                markdown.setTitle(msg.getTitle());
                markdown.setText(msg.getText());
                request.setMarkdown(markdown);

			};break;
			case text:{

				TextMessage msg = (TextMessage) message;

				OapiRobotSendRequest.Text text = new OapiRobotSendRequest.Text();
				text.setContent(msg.getContent());
				request.setText(text);

			};break;
		}

        OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
        if(ArrayUtils.isNotEmpty(message.getAtMobiles())){
            at.setAtMobiles(Arrays.asList(message.getAtMobiles()));
        }
        if(ArrayUtils.isNotEmpty(message.getAtUserIds())){
            at.setAtUserIds(Arrays.asList(message.getAtUserIds()));
        }
        at.setIsAtAll(message.isAtAll());
        request.setAt(at);
		return request;
    }

    /**
     * 发送钉钉机器人文本消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param message 消息对象
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    private <M extends BaseMessage> OapiRobotSendResponse sendMessage(String corpId, String robotId, M message) throws ApiException {
        Long timestamp = System.currentTimeMillis();
        DingTalkClient client = new DefaultDingTalkClient(this.getWebhook(corpId, robotId, timestamp));
        OapiRobotSendRequest request = this.buidRequest(message);
        request.setTimestamp(timestamp);
        return client.execute(request);
	}

    /**
     * 发送钉钉机器人文本消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param message 消息对象
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendTextMessage(String corpId, String robotId, TextMessage message) throws ApiException {
        return this.sendMessage(corpId, robotId, message);
    }

    /**
     * 发送钉钉机器人文本消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param content 消息内容
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendTextMessage(String corpId, String robotId, String content) throws ApiException {
        return this.sendMessage(corpId, robotId, new TextMessage(content));
    }

    /**
     * 发送钉钉机器人文本消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param content 消息内容
     * @param isAtAll 是否@所有人
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendTextMessage(String corpId, String robotId, String content, boolean isAtAll) throws ApiException {
        TextMessage message = new TextMessage(content, isAtAll);
        return this.sendMessage(corpId, robotId, message);
    }

    /**
     * 发送钉钉机器人文本消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param content 消息内容
     * @param atMobiles 被@人的手机号
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendTextMessage(String corpId, String robotId, String content, String[] atMobiles) throws ApiException {
        TextMessage message = new TextMessage(content, atMobiles);
        return this.sendMessage(corpId, robotId, message);
    }

    /**
     * 发送钉钉机器人Link消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param message 消息对象
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendLinkMessage(String corpId, String robotId, LinkMessage message) throws ApiException {
        return this.sendMessage(corpId, robotId, message);
    }

    /**
     * 发送钉钉机器人Link消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param title 消息标题
     * @param text 消息内容
     * @param messageUrl 消息跳转URL
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendLinkMessage(String corpId, String robotId, String title, String text, String messageUrl) throws ApiException {
        return this.sendMessage(corpId, robotId, new LinkMessage(title, text, messageUrl));
    }

    /**
     * 发送钉钉机器人Link消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param title 消息标题
     * @param text 消息内容
     * @param messageUrl 消息跳转URL
     * @param picUrl 封面图片URL
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendLinkMessage(String corpId, String robotId, String title, String text, String messageUrl, String picUrl) throws ApiException {
        return this.sendMessage(corpId, robotId, new LinkMessage(title, text, messageUrl, picUrl));
    }

    /**
     * 发送钉钉机器人MarkDown消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param message 消息对象
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendMarkdownMessage(String corpId, String robotId, MarkdownMessage message) throws ApiException {
        return this.sendMessage(corpId, robotId, message);
    }

    /**
     * 发送钉钉机器人MarkDown消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param title 消息标题
     * @param text 消息内容
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendMarkdownMessage(String corpId, String robotId, String title, String text) throws ApiException {
        return this.sendMessage(corpId, robotId, new MarkdownMessage(title, text));
    }

    /**
     * 发送钉钉机器人MarkDown消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param title 消息标题
     * @param text 消息内容
     * @param isAtAll 是否@所有人
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendMarkdownMessage(String corpId, String robotId, String title, String text, boolean isAtAll) throws ApiException {
        return this.sendMessage(corpId, robotId, new MarkdownMessage(title, text, isAtAll));
    }

    /**
     * 发送钉钉机器人MarkDown消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param title 消息标题
     * @param text 消息内容
     * @param atMobiles 被@人的手机号
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendMarkdownMessage(String corpId, String robotId, String title, String text, String[] atMobiles) throws ApiException {
        return this.sendMessage(corpId, robotId, new MarkdownMessage(title, text, atMobiles));
    }

    /**
     * 发送钉钉机器人ActionCard消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param message 消息对象
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendActionCardMessage(String corpId, String robotId, ActionCardMessage message) throws ApiException {
        return this.sendMessage(corpId, robotId, message);
    }

    /**
     * 发送钉钉机器人ActionCard消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param title 消息标题
     * @param text 消息内容
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendActionCardMessage(String corpId, String robotId, String title, String text) throws ApiException {
        return this.sendMessage(corpId, robotId, new ActionCardMessage(title, text));
    }

    /**
     * 发送钉钉机器人ActionCard消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param title 消息标题
     * @param text 消息内容
     * @param hideAvatar 是否隐藏头像
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendActionCardMessage(String corpId, String robotId, String title, String text, HideAvatarType hideAvatar) throws ApiException {
        return this.sendMessage(corpId, robotId, new ActionCardMessage(title, text, hideAvatar));
    }

    /**
     * 发送钉钉机器人ActionCard消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param title 消息标题
     * @param text 消息内容
     * @param button 操作按钮
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendActionCardMessage(String corpId, String robotId, String title, String text, ActionCardButton button) throws ApiException {
        return this.sendMessage(corpId, robotId, new ActionCardMessage(title, text, button));
    }

    /**
     * 发送钉钉机器人ActionCard消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param title 消息标题
     * @param text 消息内容
     * @param hideAvatar 是否隐藏头像
     * @param button 操作按钮
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendActionCardMessage(String corpId, String robotId, String title, String text, HideAvatarType hideAvatar, ActionCardButton button) throws ApiException {
        return this.sendMessage(corpId, robotId, new ActionCardMessage(title, text, hideAvatar, button));
    }

    /**
     * 发送钉钉机器人FeedCard消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param message 消息对象
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendFeedCardMessage(String corpId, String robotId, FeedCardMessage message) throws ApiException {
        return this.sendMessage(corpId, robotId, message);
    }

    /**
     * 发送钉钉机器人FeedCard消息
     * @param corpId 企业corpId
     * @param robotId 机器人应用Id
     * @param feedCardItems 消息明细条目
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public OapiRobotSendResponse sendFeedCardMessage(String corpId, String robotId, List<FeedCardMessageItem> feedCardItems) throws ApiException {
        return this.sendMessage(corpId, robotId, new FeedCardMessage(feedCardItems));
    }

    /**
     * 发送钉钉机器人FeedCard消息
     * @param webhookUrl 企业corpId
     * @param secret 机器人应用Id
     * @param message 消息对象
     * @return 消息发送结果
     * @throws ApiException 如果出错，则抛出异常
     */
    public <M extends BaseMessage> OapiRobotSendResponse sendMessageByUrl(String webhookUrl, String secret, M message) throws ApiException {

        Long timestamp = System.currentTimeMillis();
        String sign = template.getSign(secret, timestamp);
        StringBuilder serverUrl = new StringBuilder(webhookUrl).append("&timestamp=").append(timestamp).append("&sign=").append(sign);
        DingTalkClient client = new DefaultDingTalkClient(serverUrl.toString());

        OapiRobotSendRequest request = this.buidRequest(message);
        request.setTimestamp(timestamp);
        return client.execute(request);
	}

}
