package com.dingtalk.spring.boot.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Dingding机器人配置
 * 
 * @author ： <a href="https://github.com/hiwepy">wandl</a>
 */
@Getter
@Setter
@ToString
public class DingTalkRobotProperties {

	/**
	 * 钉钉机器人ID
	 */
	private String robotId;
	
	/**
	 * 钉钉机器人WebHook地址的access_token
	 */
	private String accessToken;

	/**
	 * 钉钉机器人WebHook地址的secret_token,群机器人加签用
	 */
	private String secretToken;

}
