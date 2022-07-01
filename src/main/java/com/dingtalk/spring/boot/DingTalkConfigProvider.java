package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.property.*;

public interface DingTalkConfigProvider {

    /**
     * 根据cropId获取所有的钉钉配置
     * @param cropId  企业ID
     * @return
     */
    DingTalkProperties getDingTalkProperties(String cropId);

    /**
     * 根据cropId、agentId 获取企业内部开发：小程序、H5配置
     * @param cropId  企业ID
     * @param agentId 程序客户端ID
     * @return
     */
    DingTalkCropAppProperties getDingTalkCropAppProperties(String cropId, String agentId);

    /**
     * 根据 cropId、appId 获取第三方个人应用：小程序配置
     * @param cropId  企业ID
     * @param appId   应用Id
     * @return
     */
    DingTalkPersonalMiniAppProperties getDingTalkPersonalMiniAppProperties(String cropId, String appId);

    /**
     * 根据 cropId、suiteId 获取第三方企业应用：小程序、H5配置
     * @param cropId  企业ID
     * @param suiteId 程序客户端ID
     * @return
     */
    DingTalkSuiteProperties getDingTalkSuiteProperties(String cropId, String suiteId);

    /**
     * 根据 cropId、appId 获取钉钉扫码登录配置
     * @param cropId  企业ID
     * @param appId   应用Id
     * @return
     */
    DingTalkLoginProperties getDingTalkLoginProperties(String cropId, String appId);

    /**
     * 根据 cropId、appId 获取钉钉机器人配置
     * @param cropId  企业ID
     * @param robotId 机器人ID
     * @return
     */
    DingTalkRobotProperties getDingTalkRobotProperties(String cropId, String robotId);

    boolean hasAppKey(String cropId, String appKey);

    /**
     * 企业的密钥
     * @param cropId  企业ID
     * @return 企业的密钥
     */
    String getCorpSecret(String cropId);

    String getAppSecret(String cropId, String appKey);

}
