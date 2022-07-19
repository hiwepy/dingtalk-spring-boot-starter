package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.property.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultDingTalkConfigProvider implements DingTalkConfigProvider, InitializingBean {

    private final DingTalkProperties dingTalkProperties;
    private Map<String, String> appKeySecret = new ConcurrentHashMap<>();

    public DefaultDingTalkConfigProvider(DingTalkProperties dingTalkProperties) {
        this.dingTalkProperties = dingTalkProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if(!CollectionUtils.isEmpty(this.dingTalkProperties.getCorpApps())) {
            for (DingTalkCorpAppProperties properties : this.dingTalkProperties.getCorpApps()) {
                appKeySecret.put(properties.getAppKey(), properties.getAppSecret());
            }
        }
        if(!CollectionUtils.isEmpty(this.dingTalkProperties.getApps())) {
            for (DingTalkPersonalMiniAppProperties properties : this.dingTalkProperties.getApps()) {
                appKeySecret.put(properties.getAppId(), properties.getAppSecret());
            }
        }
        if(!CollectionUtils.isEmpty(this.dingTalkProperties.getSuites())) {
            for (DingTalkSuiteProperties properties : this.dingTalkProperties.getSuites()) {
                appKeySecret.put(properties.getAppId(), properties.getSuiteSecret());
            }
        }
        if(!CollectionUtils.isEmpty(this.dingTalkProperties.getLogins())) {
            for (DingTalkLoginProperties properties : this.dingTalkProperties.getLogins()) {
                appKeySecret.put(properties.getAppId(), properties.getAppSecret());
            }
        }

    }

    @Override
    public DingTalkProperties getDingTalkProperties(String corpId) {
        return dingTalkProperties;
    }

    @Override
    public DingTalkCorpAppProperties getDingTalkCorpAppProperties(String corpId, String agentId) {
        if(CollectionUtils.isEmpty(dingTalkProperties.getCorpApps())){
            return null;
        }
        Optional<DingTalkCorpAppProperties> optional = dingTalkProperties.getCorpApps().stream()
                .filter(item -> StringUtils.equals(item.getAgentId(), agentId)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public DingTalkPersonalMiniAppProperties getDingTalkPersonalMiniAppProperties(String corpId, String appId) {
        if(CollectionUtils.isEmpty(dingTalkProperties.getApps())){
            return null;
        }
        Optional<DingTalkPersonalMiniAppProperties> optional = dingTalkProperties.getApps().stream()
                .filter(item -> StringUtils.equals(item.getAppId(), appId)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public DingTalkSuiteProperties getDingTalkSuiteProperties(String corpId, String suiteId) {
        if(CollectionUtils.isEmpty(dingTalkProperties.getSuites())){
            return null;
        }
        Optional<DingTalkSuiteProperties> optional = dingTalkProperties.getSuites().stream()
                .filter(item -> StringUtils.equals(item.getSuiteId(), suiteId)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public DingTalkLoginProperties getDingTalkLoginProperties(String corpId, String appId) {
        if(CollectionUtils.isEmpty(dingTalkProperties.getLogins())){
            return null;
        }
        Optional<DingTalkLoginProperties> optional = dingTalkProperties.getLogins().stream()
                .filter(item -> StringUtils.equals(item.getAppId(), appId)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public DingTalkRobotProperties getDingTalkRobotProperties(String corpId, String robotId) {
        if(CollectionUtils.isEmpty(dingTalkProperties.getRobots())){
            return null;
        }
        Optional<DingTalkRobotProperties> optional = dingTalkProperties.getRobots().stream()
                .filter(item -> StringUtils.equals(item.getRobotId(), robotId)).findFirst();
        return optional.isPresent() ? optional.get() : null;
    }

    @Override
    public boolean hasAppKey(String appKey) {
        return appKeySecret.containsKey(appKey);
    }

    @Override
    public String getCorpId(String appKey) {
        return dingTalkProperties.getCorpId();
    }

    @Override
    public String getCorpSecret(String corpId) {
        return dingTalkProperties.getCorpSecret();
    }

    @Override
    public String getAppSecret(String corpId, String appKey) {
        String appSecret = appKeySecret.get(appKey);
        return appSecret;
    }

}
