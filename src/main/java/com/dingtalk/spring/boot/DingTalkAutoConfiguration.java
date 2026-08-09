package com.dingtalk.spring.boot;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dingtalk.api.DingTalkClient;

/**
 * Auto-configuration for DingTalk integration, registering the default {@link DingTalkConfigProvider},
 * {@link DingTalkAccessTokenProvider}, and {@link DingTalkTemplate} beans.
 *
 * @author [@Loong Wan](https://github.com/loong10k)
 * @since 1.0.0
 */
@Configuration
@ConditionalOnClass({ DingTalkClient.class })
@EnableConfigurationProperties({ DingTalkProperties.class })
public class DingTalkAutoConfiguration {

	/**
	 * Registers the default {@link DingTalkConfigProvider} bean.
	 *
	 * @param dingtalkProperties the DingTalk configuration properties
	 * @return the config provider
	 */
	@Bean
	@ConditionalOnMissingBean
	public DingTalkConfigProvider dingTalkConfigProvider(DingTalkProperties dingtalkProperties){
		return new DefaultDingTalkConfigProvider(dingtalkProperties);
	}

	/**
	 * Registers the default {@link DingTalkAccessTokenProvider} bean.
	 *
	 * @param dingTalkConfigProvider the DingTalk config provider
	 * @return the access token provider
	 */
	@Bean
	@ConditionalOnMissingBean
	public DingTalkAccessTokenProvider dingTalkAccessTokenProvider(ObjectProvider<DingTalkConfigProvider> dingTalkConfigProvider){
		return new DefaultDingTalkAccessTokenProvider(dingTalkConfigProvider.getIfAvailable());
	}

	/**
	 * Registers the {@link DingTalkTemplate} bean for executing DingTalk API operations.
	 *
	 * @param dingTalkConfigProvider the DingTalk config provider
	 * @param dingTalkAccessTokenProvider the DingTalk access token provider
	 * @return the DingTalk template
	 */
	@Bean
	@ConditionalOnMissingBean
	public DingTalkTemplate dingtalkTemplate(ObjectProvider<DingTalkConfigProvider> dingTalkConfigProvider,
											 ObjectProvider<DingTalkAccessTokenProvider> dingTalkAccessTokenProvider) {
		return new DingTalkTemplate(dingTalkConfigProvider.getIfAvailable(), dingTalkAccessTokenProvider.getIfAvailable());
	}

}



