package com.dingtalk.spring.boot;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dingtalk.api.DingTalkClient;

@Configuration
@ConditionalOnClass({ DingTalkClient.class })
@EnableConfigurationProperties({ DingTalkProperties.class })
public class DingTalkAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public DingTalkConfigProvider dingTalkConfigProvider(DingTalkProperties dingtalkProperties){
		return new DefaultDingTalkConfigProvider(dingtalkProperties);
	}

	@Bean
	@ConditionalOnMissingBean
	public DingTalkAccessTokenProvider dingTalkAccessTokenProvider(ObjectProvider<DingTalkConfigProvider> dingTalkConfigProvider){
		return new DefaultDingTalkAccessTokenProvider(dingTalkConfigProvider.getIfAvailable());
	}

	@Bean
	@ConditionalOnMissingBean
	public DingTalkTemplate dingtalkTemplate(ObjectProvider<DingTalkConfigProvider> dingTalkConfigProvider,
											 ObjectProvider<DingTalkAccessTokenProvider> dingTalkAccessTokenProvider) {
		return new DingTalkTemplate(dingTalkConfigProvider.getIfAvailable(), dingTalkAccessTokenProvider.getIfAvailable());
	}

}



