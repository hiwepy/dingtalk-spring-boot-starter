package com.dingtalk.spring.boot.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RobotSendBO {

	/**
	 * 消息通知类型
	 */
	private InformType type;
	/**
	 * 消息通知标题（可能包含变量）
	 */
	private String title;
	/**
	 * 消息通知内容（可能包含变量）
	 */
	private String content;
	/**
	 * 消息通知跳转URL
	 */
	private String jumpUrl;
	/**
	 * 封面图片URL
	 */
	private String picUrl;
	/**
	 * 模板消息通知对应第三方平台内的模板id
	 */
	private String templateId;
	/**
	 * 消息通知模板参数
	 */
	private Map<String, Object> templateParams;
	/**
	 * 消息通知变量载体,JOSN格式的数据
	 */
	private String payload;
	/**
	 * 消息通知接收人ID集合（接口只提供发送到人的能力）
	 */
	private List<Long> toList;
	/**
	 * 消息通知内容中包含的路由跳转信息（JSON格式：[{"name":"路由名称","word":"路由文字","link":"路由跳转地址"}]）
	 */
	private List<MessageRouteBO> routes;

}
