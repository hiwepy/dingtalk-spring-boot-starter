package com.dingtalk.spring.boot.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for DingTalk robots, including webhook access token and signing secret.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
/**
 * <p>Auto-configuration for DingTalkRobotProperties.</p>
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class DingTalkRobotProperties {

	/** The DingTalk robot ID. */
	private String robotId;
	/** The access token from the robot webhook URL. */
	private String accessToken;
	/** The secret token for robot webhook signing. */
	private String secretToken;

}
