package io.renren.modules.app.service.impl;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSONObject;
import io.renren.common.constants.AppConstants;
import io.renren.common.exception.RRException;
import io.renren.modules.app.entity.vo.SubscriptionMessageVO;
import io.renren.modules.app.service.ApiWexinService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Service("wexinService")
@Slf4j
public class WexinServiceImpl implements ApiWexinService {
    public static final String GET = "GET";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String EXPIRES_IN = "expires_in";
    public static final String REFRESH_TOKEN = "refresh_token";
    public static final String OPENID = "openid";
    public static final String SCOPE = "scope";
    public static final String ERRCODE = "errcode";
    public static final String ERRMSG = "errmsg";


    TimedCache<String, String> timedCache = CacheUtil.newTimedCache(6000 * 1000);

    public WexinServiceImpl() {
        timedCache.schedulePrune(5);
    }

    /**
     * 微信小程序登录
     *
     * @param wxAppid
     * @param wxSecret
     * @param code
     * @return
     */
    public Map<String, Object> getMiniProgramOpenId(String wxAppid, String wxSecret, String code) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("SECRET", wxSecret).replace("APPID", wxAppid).replace("JSCODE", code);
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", (String) null);
        Map<String, Object> map = new HashMap();
        if (!jsonObject.containsKey("errcode")) {
            map.put("openId", jsonObject.get("openid"));
            map.put("sessionKey", jsonObject.get("session_key"));
        }

        return map;
    }

    /**
     * qq小程序登录
     * @param code
     * @param appId
     * @param secret
     * @return
     */
    public Map<String, Object> getQQMiniProgramOpenId(String code,String appId,String secret) {
        String requestUrl = "https://api.q.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";
        requestUrl = requestUrl.replace("SECRET", secret).replace("APPID", appId).replace("JSCODE", code);
        JSONObject jsonObject = httpsRequest(requestUrl, "GET", (String) null);
        Map<String, Object> map = new HashMap();
        Integer errcode = jsonObject.getInteger("errcode");
        if (errcode == 0) {
            map.put("openId", jsonObject.get("openid"));
            map.put("sessionKey", jsonObject.get("session_key"));
        }

        return map;
    }

    /**
     * 获取QQ小程序 access_token
     * <p>
     * 文档地址：https://q.qq.com/wiki/develop/miniprogram/server/open_port/port_use.html#getaccesstoken
     *
     * @return
     */
    public String getQqAccessToken() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("grant_type", "client_credential");
        // TODO: 2019/12/31  
//        paramMap.put("appid", AppConstants.App.Yima.QQ.APP_ID);
//        paramMap.put("secret", AppConstants.App.Yima.QQ.SECRET);

        String result3 = HttpUtil.get("https://api.q.qq.com/api/getToken", paramMap);
        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(result3);

        String errcode = String.valueOf(jsonObject.get("errcode"));
        String access_token = String.valueOf(jsonObject.get("access_token"));
        if (!"0".equals(errcode)) {

            //-1	系统繁忙，此时请开发者稍候再试
            //0	请求成功
            //40001	AppSecret 错误或者 AppSecret 不属于这个小程序，请开发者确认 AppSecret 的正确性
            //40002	请确保 grant_type 字段值为 client_credential
            //40013	不合法的 AppID，请开发者检查 AppID 的正确性，避免异常字符，注意大小写

            log.error("请求QQ小程序accessToken异常：{}", result3);
            throw new RRException(String.valueOf(jsonObject.get("errmsg")));

        }

        return access_token;
    }

    /**
     * 从缓存或qq获取accessToken
     *
     * @return
     */
    public String getQqAccessTokenFromCacheOrQQ() {
        String accessToken = timedCache.get("accessToken");
        if (StrUtil.isEmpty(accessToken) || "null".equals(accessToken)) {
            accessToken = getQqAccessToken();
            timedCache.put("accessToken", accessToken);
        }
        return accessToken;
    }

    /**
     * 发送订阅消息
     * <p>
     * 文档地址：https://q.qq.com/wiki/develop/game/server/open-port/port_subscrib.html#sendsubscriptionmessage
     *
     * @return
     */
    public String sendSubscriptionMessage(SubscriptionMessageVO messageVO) {
        String accessToken = getQqAccessTokenFromCacheOrQQ();
        if (StrUtil.isEmpty(accessToken) || "null".equals(accessToken)) {
            log.error("accessToken为空", accessToken);
            throw new RRException("accessToken为空");
        }
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", accessToken);
        paramMap.put("touser", messageVO.getToUser());
        paramMap.put("template_id", messageVO.getTemplateId());
        paramMap.put("page", messageVO.getPage());
        paramMap.put("data", messageVO.getData());
        paramMap.put("emphasis_keyword", messageVO.getEmphasisKeyword());
        String url = "https://api.q.qq.com/api/json/subscribe/SendSubscriptionMessage?access_token=" + accessToken;

        //String jsonResult = HttpUtil.post(url, paramMap);

        JSON json= JSONUtil.parse(paramMap);
        //链式构建请求
        String jsonResult = HttpRequest.post(url)
                .header(Header.CONTENT_TYPE, "application/json")//头信息
                .body(json.toString())
                .timeout(20000)//超时，毫秒
                .execute().body();

        cn.hutool.json.JSONObject jsonObject = JSONUtil.parseObj(jsonResult);

        String errcode = String.valueOf(jsonObject.get("errcode"));
        if (!"0".equals(errcode)) {

            //40037	template_id不正确
            //40014	token过期
            //40036	内部错误，参照msg返回信息确认问题
            //40037	template_id不正确，参照msg返回信息确认问题
            //41030	对同一用户推送请求太快
            //46001	用户未订阅
            //46002	当日超过推送限额
            //other	联系对接同学

            log.error("发送订阅消息异常：{}", jsonResult);
            throw new RRException(String.valueOf(jsonObject.get("errmsg")));

        }
        return jsonResult;


    }


    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;

        try {
            URL httpUrl = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(requestMethod);
            conn.connect();
            if (null != outputStr) {
                OutputStream outputStream = conn.getOutputStream();
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();

            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            conn.disconnect();
            jsonObject = JSONObject.parseObject(buffer.toString());
        } catch (ConnectException var11) {
            log.error("连接超时：{}", var11);
        } catch (Exception var12) {
            log.error("https请求异常：{}", var12);
        }

        return jsonObject;
    }
}