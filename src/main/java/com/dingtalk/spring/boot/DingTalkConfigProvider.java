package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.property.*;

import java.util.Optional;

public interface DingTalkConfigProvider {

    /**
     * 根据corpId获取所有的钉钉配置
     * @param corpId  企业ID
     * @return
     */
    Optional<DingTalkProperties> getDingTalkProperties(String corpId);

    /**
     * 根据corpId、agentId 获取企业内部开发：小程序、H5配置
     * @param corpId  企业ID
     * @param agentId 程序客户端ID
     * @return
     */
    Optional<DingTalkCorpAppProperties> getDingTalkCorpAppProperties(String corpId, String agentId);

    /**
     * 根据 corpId、appId 获取第三方个人应用：小程序配置
     * @param corpId  企业ID
     * @param appId   应用Id
     * @return
     */
    Optional<DingTalkPersonalMiniAppProperties> getDingTalkPersonalMiniAppProperties(String corpId, String appId);

    /**
     * 根据 corpId、suiteId 获取第三方企业应用：小程序、H5配置
     * @param corpId  企业ID
     * @param suiteId 程序客户端ID
     * @return
     */
    Optional<DingTalkSuiteProperties> getDingTalkSuiteProperties(String corpId, String suiteId);

    /**
     * 根据 corpId、appId 获取钉钉扫码登录配置
     * @param corpId  企业ID
     * @param appId   应用Id
     * @return
     */
    Optional<DingTalkLoginProperties> getDingTalkLoginProperties(String corpId, String appId);

    /**
     * 根据 corpId、appId 获取钉钉机器人配置
     * @param corpId  企业ID
     * @param robotId 机器人ID
     * @return
     */
    Optional<DingTalkRobotProperties> getDingTalkRobotProperties(String corpId, String robotId);

    boolean hasAppKey(String appKey);

    /**
     * 通过应用Key或Id获取corpId
     * @param appKey 应用Key或Id
     * @return 企业的corpId
     */
    String getCorpId(String appKey);

    /**
     * 企业的密钥
     * @param corpId  企业ID
     * @return 企业的密钥
     */
    String getCorpSecret(String corpId);

    String getAppSecret(String corpId, String appKey);

}
