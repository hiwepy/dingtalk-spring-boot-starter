package com.dingtalk.spring.boot.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for DingTalk mobile access application scan-to-login.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
/**
 * <p>Auto-configuration for DingTalkLoginProperties.</p>
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class DingTalkLoginProperties {

	/** The scan-to-login application ID. */
	private String appId;
	/** The scan-to-login application secret. */
	private String appSecret;

}
