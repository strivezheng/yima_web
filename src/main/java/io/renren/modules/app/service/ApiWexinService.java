package io.renren.modules.app.service;

import io.renren.modules.app.entity.vo.SubscriptionMessageVO;

import java.util.Map;

public interface ApiWexinService {



    Map<String, Object> getMiniProgramOpenId(String wxAppid, String wxSecret, String code);

    /**
     * qq小程序登录
     * @param code
     * @param appId
     * @param secret
     * @return
     */
    public Map<String, Object> getQQMiniProgramOpenId(String code,String appId,String secret);


    /**
     * 获取QQ小程序 access_token
     * <p>
     * 文档地址：https://q.qq.com/wiki/develop/miniprogram/server/open_port/port_use.html#getaccesstoken
     *
     * @return
     */
    public String getQqAccessToken();

    /**
     * 发送订阅消息
     * <p>
     * 文档地址：https://q.qq.com/wiki/develop/game/server/open-port/port_subscrib.html#sendsubscriptionmessage
     *
     * @return
     */
    public String  sendSubscriptionMessage(SubscriptionMessageVO messageVO);
}
