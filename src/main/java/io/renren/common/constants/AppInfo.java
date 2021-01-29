package io.renren.common.constants;

import lombok.Data;

@Data
public class AppInfo {

    /**
     * appId
     */
    private String appId;

    /**
     * secret
     */
    private String secret;

    /**
     * 名称
     */
    private String appName;

    /**
     * token
     */
    private String appToken;

    /**
     * 平台 qq/wx
     */
    private String platform;

    /**
     * 客户端
     */
    private String client;



}
