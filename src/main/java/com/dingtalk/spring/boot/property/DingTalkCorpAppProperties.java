package com.dingtalk.spring.boot.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for DingTalk enterprise internal applications (mini apps, H5).
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
/**
 * <p>Auto-configuration for DingTalkCorpAppProperties.</p>
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public class DingTalkCorpAppProperties {

	/** The application agent ID. */
	private String agentId;
	/** The unique application key. */
	private String appKey;
	/** The application secret. */
	private String appSecret;

}
