package com.dingtalk.spring.boot;

import com.dingtalk.spring.boot.property.*;

/**
 * Provider interface for accessing DingTalk configuration properties for various application types
 * including enterprise internal apps, personal mini apps, suites, login apps, and robots.
 *
 * @author <a href="https://github.com/loong10k">Loong Wan</a>
 * @since 1.0.0
 */
public interface DingTalkConfigProvider {

    /**
     * Retrieves all DingTalk configuration properties for the given enterprise.
     *
     * @param corpId  the enterprise ID
     * @return the DingTalk properties
     */
    DingTalkProperties getDingTalkProperties(String corpId);

    /**
     * Retrieves the enterprise internal application (mini app or H5) configuration.
     *
     * @param corpId  the enterprise ID
     * @param agentId the application agent ID
     * @return the enterprise app properties, or null if not found
     */
    DingTalkCorpAppProperties getDingTalkCorpAppProperties(String corpId, String agentId);

    /**
     * Retrieves the third-party personal mini app configuration.
     *
     * @param corpId  the enterprise ID
     * @param appId   the application ID
     * @return the personal mini app properties, or null if not found
     */
    DingTalkPersonalMiniAppProperties getDingTalkPersonalMiniAppProperties(String corpId, String appId);

    /**
     * Retrieves the third-party enterprise application (mini app or H5) configuration.
     *
     * @param corpId  the enterprise ID
     * @param suiteId the suite ID
     * @return the suite properties, or null if not found
     */
    DingTalkSuiteProperties getDingTalkSuiteProperties(String corpId, String suiteId);

    /**
     * Retrieves the DingTalk scan-to-login configuration.
     *
     * @param corpId  the enterprise ID
     * @param appId   the application ID
     * @return the login properties, or null if not found
     */
    DingTalkLoginProperties getDingTalkLoginProperties(String corpId, String appId);

    /**
     * Retrieves the DingTalk robot configuration.
     *
     * @param corpId  the enterprise ID
     * @param robotId the robot ID
     * @return the robot properties, or null if not found
     */
    DingTalkRobotProperties getDingTalkRobotProperties(String corpId, String robotId);

    /**
     * Checks whether the given app key is registered in the configuration.
     *
     * @param appKey the application key or ID
     * @return true if the app key exists
     */
    boolean hasAppKey(String appKey);

    /**
     * Retrieves the enterprise ID (corpId) for the given application key.
     *
     * @param appKey the application key or ID
     * @return the enterprise corpId
     */
    String getCorpId(String appKey);

    /**
     * Retrieves the enterprise secret for the given enterprise ID.
     *
     * @param corpId  the enterprise ID
     * @return the enterprise secret
     */
    String getCorpSecret(String corpId);

    /**
     * Retrieves the application secret for the given enterprise and application key.
     *
     * @param corpId  the enterprise ID
     * @param appKey  the application key
     * @return the application secret
     */
    String getAppSecret(String corpId, String appKey);

}
