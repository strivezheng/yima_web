package io.renren.modules.app.entity.vo;

import lombok.Data;

import java.util.HashMap;

@Data
public class SubscriptionMessageVO {

    /**
     * 完整请求示例
     */

//    {
//        "access_token": "Ux56RIqCsYW27buONvyq7kdhtPzS38juiS3rzHTf1ssj6Ig8hiGMIA5kmMfZ7CsD4Z29",
//            "touser": "E7C8FB9DB99E318094E437849454D6A7",
//            "template_id": "9fa50cc3065488fb4bffb3a928bec249",
//            "data": {
//                "keyword1": {
//                    "value": "姨妈开始提醒"
//                },
//                "keyword2": {
//                    "value": "2019年5月05日（三天后）"
//                },
//                "keyword3": {
//                    "value": "大数据预测分析，三天后您的大姨妈可能到访，请注意！"
//                },
//                "keyword4": {
//                    "value": "红糖水，生姜水都可以缓解姨妈带来的疼痛"
//                },
//                "keyword5": {
//                    "value": "姨妈日历"
//                },
//                "emphasis_keyword": "keyword1.DATA"
//            }
//    }
//
    /**
     * 接收者（用户）的 openid
     */
    private String toUser;

    /**
     * 所需下发的订阅消息的id
     */
    private String templateId;

    /**
     * 点击订阅消息卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
     */
    private String page;

    /**
     * 模板内容，不填则下发空模板。具体格式请参考示例。
     */
    private HashMap data;

    /**
     * 模板需要放大的关键词，不填则默认无放大。
     */
    private String emphasisKeyword = "keyword1.DATA";



}
