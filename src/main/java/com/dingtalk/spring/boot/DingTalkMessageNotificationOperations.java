package com.dingtalk.spring.boot;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiSnsGettokenResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.spring.boot.bean.*;
import com.dingtalk.spring.boot.property.DingTalkCorpAppProperties;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <ul>
 * <li>
 * 1、消息通知：
 * https://open.dingtalk.com/document/orgapp-server/message-notification-overview
 * </li>
 * </ul>
 */
@Slf4j
public class DingTalkMessageNotificationOperations extends DingTalkOperations {

	public DingTalkMessageNotificationOperations(DingTalkTemplate template) {
		super(template);
	}

	OapiMessageCorpconversationAsyncsendV2Request buidRequest(BaseMessage message){
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();

		OapiMessageCorpconversationAsyncsendV2Request.Msg rmsg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();

		switch (message.getMsgtype()) {
			case actionCard:{
				ActionCardMessage msg = (ActionCardMessage) message;
				OapiMessageCorpconversationAsyncsendV2Request.ActionCard actioncard = new OapiMessageCorpconversationAsyncsendV2Request.ActionCard();
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
				rmsg.setActionCard(actioncard);
			};break;
			case feedCard:{

				FeedCardMessage msg = (FeedCardMessage) message;
				OapiMessageCorpconversationAsyncsendV2Request.Feedcard feedcard = new OapiRobotSendRequest.Feedcard();
				feedcard.setLinks(msg.getFeedCardItems().stream().map(item -> {
					OapiRobotSendRequest.Links links = new OapiRobotSendRequest.Links();
					links.setTitle(item.getTitle());
					links.setMessageURL(item.getMessageURL());
					links.setPicURL(item.getPicURL());
					return links;
				}).collect(Collectors.toList()));
				rmsg.setFeedCard(feedcard);

			};break;
			case link:{

				LinkMessage msg = (LinkMessage) message;
				OapiMessageCorpconversationAsyncsendV2Request.Link link = new OapiMessageCorpconversationAsyncsendV2Request.Link();
				link.setTitle(msg.getTitle());
				link.setText(msg.getText());
				link.setMessageUrl(msg.getMessageUrl());
				link.setPicUrl(msg.getPicUrl());
				rmsg.setLink(link);

			};break;
			case markdown:{

				MarkdownMessage msg = (MarkdownMessage) message;
				OapiMessageCorpconversationAsyncsendV2Request.Markdown markdown = new OapiMessageCorpconversationAsyncsendV2Request.Markdown();
				markdown.setTitle(msg.getTitle());
				markdown.setText(msg.getText());
				rmsg.setMarkdown(markdown);

			};break;
			case text:{

				TextMessage msg = (TextMessage) message;

				OapiMessageCorpconversationAsyncsendV2Request.Text text = new OapiMessageCorpconversationAsyncsendV2Request.Text();
				text.setContent(msg.getContent());
				rmsg.setText(text);

			};break;
		}
		request.setMsg(rmsg);
		if(ArrayUtils.isNotEmpty(message.getAtUserIds())){
			request.setUseridList(StringUtils.join(message.getAtUserIds(), ","));
		}
		request.setToAllUser(message.isAtAll());
		return request;
	}

	/**
	 * 发送钉钉机器人文本消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param message 消息对象
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	private <M extends BaseMessage> OapiMessageCorpconversationAsyncsendV2Response sendMessage(String corpId, Long agentId, M message) throws ApiException {
		Optional<DingTalkCorpAppProperties> optional = template.getDingTalkConfigProvider().getDingTalkCorpAppProperties(corpId, agentId.toString());
		if(!optional.isPresent()){
			throw new ApiException(String.format("未找到{0}，{1} 匹配的应用配置。", corpId, agentId));
		}

		Long timestamp = System.currentTimeMillis();
		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/topapi/message/corpconversation/asyncsend_v2");
		OapiMessageCorpconversationAsyncsendV2Request request = this.buidRequest(message);
		request.setTimestamp(timestamp);
		request.setAgentId(agentId);

		String accessToken = template.getAccessToken(corpId, agentId.toString());
		return client.execute(request, accessToken);
	}

	/**
	 * 发送钉钉机器人文本消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param message 消息对象
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendTextMessage(String corpId, Long agentId, TextMessage message) throws ApiException {
		return this.sendMessage(corpId, agentId, message);
	}

	/**
	 * 发送钉钉机器人文本消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param content 消息内容
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendTextMessage(String corpId, Long agentId, String content) throws ApiException {
		return this.sendMessage(corpId, agentId, new TextMessage(content));
	}

	/**
	 * 发送钉钉机器人文本消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param content 消息内容
	 * @param isAtAll 是否@所有人
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendTextMessage(String corpId, Long agentId, String content, boolean isAtAll) throws ApiException {
		TextMessage message = new TextMessage(content, isAtAll);
		return this.sendMessage(corpId, agentId, message);
	}

	/**
	 * 发送钉钉机器人文本消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param content 消息内容
	 * @param atMobiles 被@人的手机号
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendTextMessage(String corpId, Long agentId, String content, String[] atMobiles) throws ApiException {
		TextMessage message = new TextMessage(content, atMobiles);
		return this.sendMessage(corpId, agentId, message);
	}

	/**
	 * 发送钉钉机器人Link消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param message 消息对象
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendLinkMessage(String corpId, Long agentId, LinkMessage message) throws ApiException {
		return this.sendMessage(corpId, agentId, message);
	}

	/**
	 * 发送钉钉机器人Link消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param title 消息标题
	 * @param text 消息内容
	 * @param messageUrl 消息跳转URL
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendLinkMessage(String corpId, Long agentId, String title, String text, String messageUrl) throws ApiException {
		return this.sendMessage(corpId, agentId, new LinkMessage(title, text, messageUrl));
	}

	/**
	 * 发送钉钉机器人Link消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param title 消息标题
	 * @param text 消息内容
	 * @param messageUrl 消息跳转URL
	 * @param picUrl 封面图片URL
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendLinkMessage(String corpId, Long agentId, String title, String text, String messageUrl, String picUrl) throws ApiException {
		return this.sendMessage(corpId, agentId, new LinkMessage(title, text, messageUrl, picUrl));
	}

	/**
	 * 发送钉钉机器人MarkDown消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param message 消息对象
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendMarkdownMessage(String corpId, Long agentId, MarkdownMessage message) throws ApiException {
		return this.sendMessage(corpId, agentId, message);
	}

	/**
	 * 发送钉钉机器人MarkDown消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param title 消息标题
	 * @param text 消息内容
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendMarkdownMessage(String corpId, Long agentId, String title, String text) throws ApiException {
		return this.sendMessage(corpId, agentId, new MarkdownMessage(title, text));
	}

	/**
	 * 发送钉钉机器人MarkDown消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param title 消息标题
	 * @param text 消息内容
	 * @param isAtAll 是否@所有人
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendMarkdownMessage(String corpId, Long agentId, String title, String text, boolean isAtAll) throws ApiException {
		return this.sendMessage(corpId, agentId, new MarkdownMessage(title, text, isAtAll));
	}

	/**
	 * 发送钉钉机器人MarkDown消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param title 消息标题
	 * @param text 消息内容
	 * @param atMobiles 被@人的手机号
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendMarkdownMessage(String corpId, Long agentId, String title, String text, String[] atMobiles) throws ApiException {
		return this.sendMessage(corpId, agentId, new MarkdownMessage(title, text, atMobiles));
	}

	/**
	 * 发送钉钉机器人ActionCard消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param message 消息对象
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendActionCardMessage(String corpId, Long agentId, ActionCardMessage message) throws ApiException {
		return this.sendMessage(corpId, agentId, message);
	}

	/**
	 * 发送钉钉机器人ActionCard消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param title 消息标题
	 * @param text 消息内容
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendActionCardMessage(String corpId, Long agentId, String title, String text) throws ApiException {
		return this.sendMessage(corpId, agentId, new ActionCardMessage(title, text));
	}

	/**
	 * 发送钉钉机器人ActionCard消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param title 消息标题
	 * @param text 消息内容
	 * @param hideAvatar 是否隐藏头像
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendActionCardMessage(String corpId, Long agentId, String title, String text, HideAvatarType hideAvatar) throws ApiException {
		return this.sendMessage(corpId, agentId, new ActionCardMessage(title, text, hideAvatar));
	}

	/**
	 * 发送钉钉机器人ActionCard消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param title 消息标题
	 * @param text 消息内容
	 * @param button 操作按钮
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendActionCardMessage(String corpId, Long agentId, String title, String text, ActionCardButton button) throws ApiException {
		return this.sendMessage(corpId, agentId, new ActionCardMessage(title, text, button));
	}

	/**
	 * 发送钉钉机器人ActionCard消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param title 消息标题
	 * @param text 消息内容
	 * @param hideAvatar 是否隐藏头像
	 * @param button 操作按钮
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendActionCardMessage(String corpId, Long agentId, String title, String text, HideAvatarType hideAvatar, ActionCardButton button) throws ApiException {
		return this.sendMessage(corpId, agentId, new ActionCardMessage(title, text, hideAvatar, button));
	}

	/**
	 * 发送钉钉机器人FeedCard消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param message 消息对象
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendFeedCardMessage(String corpId, Long agentId, FeedCardMessage message) throws ApiException {
		return this.sendMessage(corpId, agentId, message);
	}

	/**
	 * 发送钉钉机器人FeedCard消息
	 * @param corpId 企业corpId
	 * @param agentId 机器人应用Id
	 * @param feedCardItems 消息明细条目
	 * @return 消息发送结果
	 * @throws ApiException 如果出错，则抛出异常
	 */
	public OapiMessageCorpconversationAsyncsendV2Response sendFeedCardMessage(String corpId, Long agentId, List<FeedCardMessageItem> feedCardItems) throws ApiException {
		return this.sendMessage(corpId, agentId, new FeedCardMessage(feedCardItems));
	}


	public Long sendWorkNotice(String corpId, Long agentId, String accessToken, OapiMessageCorpconversationAsyncsendV2Request.Msg msg) throws ApiException {

		template.getDingTalkConfigProvider().getDingTalkCorpAppProperties(corpId, agentId.toString());

		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/topapi/message/corpconversation/asyncsend_v2");
		OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
		request.setAgentId(agentId);
		request.setUseridList("manager7675");
		request.setToAllUser(false);
		request.setMsg(msg);

		OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request, accessToken);
		if (response.isSuccess()) {
			return response.getTaskId();
		}
		return -1L;
	}

	/**
	 * 发送工作通知
	 * 调用本接口发送工作通知消息。
	 * 工作通知消息是以某个应用的名义推送到员工的工作通知消息，例如生日祝福、入职提醒等。可以发送文本、语音、链接等，消息类型和样例可参考消息类型与数据格式。
	 * https://open.dingtalk.com/document/orgapp-server/asynchronous-sending-of-enterprise-session-messages?spm=ding_open_doc.document.0.0.210d3c0bhajvqB#topic-1936877
	 * @param code 	code
	 * @param accessToken 	应用的accessToken
	 * @return the OapiUserGetuserinfoResponse
	 * @throws ApiException if Api request Exception
	 */
	public OapiUserGetuserinfoResponse getUserinfoByCode(String code, String accessToken) throws ApiException {
		DingTalkClient client = new DefaultDingTalkClient(PREFIX + "/topapi/message/corpconversation/asyncsend_v2");
		OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
		request.setCode(code);
		return client.execute(request, accessToken);
	}

}
