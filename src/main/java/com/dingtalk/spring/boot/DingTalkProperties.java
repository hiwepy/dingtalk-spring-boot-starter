package com.dingtalk.spring.boot;

import java.util.List;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.dingtalk.spring.boot.property.DingTalkCorpAppProperties;
import com.dingtalk.spring.boot.property.DingTalkLoginProperties;
import com.dingtalk.spring.boot.property.DingTalkPersonalMiniAppProperties;
import com.dingtalk.spring.boot.property.DingTalkRobotProperties;
import com.dingtalk.spring.boot.property.DingTalkSuiteProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(prefix = DingTalkProperties.PREFIX)
@Data
public class DingTalkProperties {

	public static final String PREFIX = "dingtalk";

	/**
	 * 	企业的corpid
	 */
	private String corpId;
	/**
	 * 企业的密钥
	 */
	private String corpSecret;
	/**
	 *    企业内部开发：小程序、H5配置
	 */
	private List<DingTalkCorpAppProperties> corpApps;
	/**
	 *    第三方个人应用：小程序配置
	 */
	private List<DingTalkPersonalMiniAppProperties> apps;
	/**
	 * 	第三方企业应用：小程序、H5配置
	 */
	private List<DingTalkSuiteProperties> suites;
	/**
	 *	 移动接入应用：扫码登录配置
	 */
	private List<DingTalkLoginProperties> logins;
	/**
	 *	 Dingtalk：机器人配置
	 */
	private List<DingTalkRobotProperties> robots;
	
}
