package com.dingtalk.spring.boot;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.dingtalk.spring.boot.property.DingTalkCropAppProperties;
import com.dingtalk.spring.boot.property.DingTalkLoginProperties;
import com.dingtalk.spring.boot.property.DingTalkPersonalMiniAppProperties;
import com.dingtalk.spring.boot.property.DingTalkSuiteProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ConfigurationProperties(prefix = DingTalkProperties.PREFIX)
@Getter
@Setter
@ToString
public class DingTalkProperties {

	public static final String PREFIX = "dingtalk";

	/**
	 * 	企业ID
	 */
	private String corpId;
	
	/**
	 *    企业内部开发：小程序、H5配置
	 */
	private List<DingTalkCropAppProperties> cropApps;
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
	
}
