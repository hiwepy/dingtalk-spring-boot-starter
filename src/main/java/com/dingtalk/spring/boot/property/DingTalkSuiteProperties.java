package com.dingtalk.spring.boot.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Configuration properties for DingTalk third-party enterprise applications (mini apps, H5).
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Getter
@Setter
@ToString
public class DingTalkSuiteProperties {

	/** The suite ID. */
	private String suiteId;
	/** The unique application ID. */
	private String appId;
	/** The unique application key. */
	private String suiteKey;
	/** The suite application secret. */
	private String suiteSecret;

}
