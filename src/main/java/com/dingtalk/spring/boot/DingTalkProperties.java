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

/**
 * Configuration properties for DingTalk integration, including enterprise credentials
 * and lists of application configurations for various DingTalk application types.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@ConfigurationProperties(prefix = DingTalkProperties.PREFIX)
@Data
public class DingTalkProperties {

	public static final String PREFIX = "dingtalk";

	/** The enterprise corpId. */
	private String corpId;
	/** The enterprise secret. */
	private String corpSecret;
	/** Enterprise internal application configurations (mini apps, H5). */
	private List<DingTalkCorpAppProperties> corpApps;
	/** Third-party personal mini application configurations. */
	private List<DingTalkPersonalMiniAppProperties> apps;
	/** Third-party enterprise application configurations (mini apps, H5). */
	private List<DingTalkSuiteProperties> suites;
	/** Mobile access application configurations (scan-to-login). */
	private List<DingTalkLoginProperties> logins;
	/** DingTalk robot configurations. */
	private List<DingTalkRobotProperties> robots;
	
}
