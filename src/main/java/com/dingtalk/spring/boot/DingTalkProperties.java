package com.dingtalk.spring.boot;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.dingtalk.spring.boot.property.SecurityDingTalkCropAppProperties;
import com.dingtalk.spring.boot.property.SecurityDingTalkLoginProperties;
import com.dingtalk.spring.boot.property.SecurityDingTalkPersonalMiniAppProperties;
import com.dingtalk.spring.boot.property.SecurityDingTalkSuiteProperties;

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
	private List<SecurityDingTalkCropAppProperties> cropApps;
	/**
	 *    第三方个人应用：小程序配置
	 */
	private List<SecurityDingTalkPersonalMiniAppProperties> apps;
	/**
	 * 	第三方企业应用：小程序、H5配置
	 */
	private List<SecurityDingTalkSuiteProperties> suites;
	/**
	 *	 移动接入应用：扫码登录配置
	 */
	private List<SecurityDingTalkLoginProperties> logins;
	
}
