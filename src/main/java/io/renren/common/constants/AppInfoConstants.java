package io.renren.common.constants;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;

public class AppInfoConstants {

    private final static HashMap<String, AppInfo> APP_INFO_MAP = new HashMap();

    public static final String QQ = "qq";
    public static final String WEI_XIN = "wx";


    static {

        // 姨妈日历 qq
        AppInfo appInfo = new AppInfo();
        appInfo.setAppId("1109746067");
        appInfo.setSecret("PngtlxTfEYDgvD3r");
        appInfo.setAppToken("d6149200e558888a5b79c5c331596b89");
        appInfo.setAppName("姨妈日历");
        appInfo.setPlatform("qq");
        appInfo.setClient("qq");
        APP_INFO_MAP.put("qq", appInfo);


        // 姨妈助手 qq  766
        appInfo = new AppInfo();
        appInfo.setAppId("1110157296");
        appInfo.setSecret("ltdqWFH1ZOyiDP3k");
        appInfo.setAppToken("ac7a0ab37bd473346580872bc7200459");
        appInfo.setAppName("姨妈助手");
        appInfo.setPlatform("qq");
        appInfo.setClient("qq-dymrl");
        APP_INFO_MAP.put("qq-dymrl", appInfo);


        // 姨妈助手 qq 766
//        appInfo = new AppInfo();
//        appInfo.setAppId("1110103383");
//        appInfo.setSecret("CObtkxqXI40VEjnJ");
//        appInfo.setAppToken("a537ae9a2e76ddd522b5b3619678b7f0");
//        appInfo.setAppName("姨妈助手");
//        appInfo.setPlatform("qq");
//        appInfo.setClient("qq-ymzs");
//        APP_INFO_MAP.put("qq-ymzs", appInfo);


        // 姨妈计算器 qq  113
        appInfo = new AppInfo();
        appInfo.setAppId("1110080655");
        appInfo.setSecret("MvX9Qvz5HqoAUy49");
        appInfo.setAppToken("a831c9828f722dd25c72321745d274ca");
        appInfo.setAppName("姨妈计算器");
        appInfo.setPlatform("qq");
        appInfo.setClient("qq-ymsq");
        APP_INFO_MAP.put("qq-ymsq", appInfo);


        // 云同学录 qq  766097388 strivezheng@aliyun.com
        appInfo = new AppInfo();
        appInfo.setAppId("1110160058");
        appInfo.setSecret("CTJLU1B7xBW7HAiV");
        appInfo.setAppToken("5bb9e402c4ec90fba29f955b50e7e403");
        appInfo.setAppName("云同学录");
        appInfo.setPlatform("qq");
        appInfo.setClient("qq-ytxl");
        APP_INFO_MAP.put("qq-ytxl", appInfo);


        // 云同学录 wx 113
        appInfo = new AppInfo();
        appInfo.setAppId("wxe05e88026ed89273");
        appInfo.setSecret("49f86f62fe592f167383e510b659f675");
        appInfo.setAppToken("");
        appInfo.setAppName("云同学录");
        appInfo.setPlatform("wx");
        APP_INFO_MAP.put("wx-ytxl", appInfo);

    }

    public static AppInfo getAppInfo(String client) {


        AppInfo appInfo = APP_INFO_MAP.get(client);
        if (appInfo == null) {
            //默认"姨妈日历"
            appInfo = APP_INFO_MAP.get("qq");
        }
        return appInfo;
    }


}
