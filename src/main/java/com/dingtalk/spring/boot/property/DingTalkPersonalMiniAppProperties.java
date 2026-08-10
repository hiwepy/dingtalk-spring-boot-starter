package com.dingtalk.spring.boot.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for DingTalk third-party personal mini applications.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class DingTalkPersonalMiniAppProperties {

	/** The unique application ID used to obtain the user-authorized access token. */
	private String appId;
	/** The application secret used to obtain the user-authorized access token. */
	private String appSecret;

}
