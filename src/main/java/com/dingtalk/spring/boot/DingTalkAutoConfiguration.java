package com.dingtalk.spring.boot;

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
	public DingTalkTemplate dingtalkTemplate(DingTalkProperties dingtalkProperties) {
		return new DingTalkTemplate(dingtalkProperties);
	}

}



