package com.jh.jsuk.utils.wx;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class JsapiTicketUtil {
    // 网页授权接口
    public final static String GetPageAccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";
    public final static String APP_ID = "wx5c8b89838ce96f7e";
    public final static String APP_SECRET = "a4a07ee1f5e89eb40429e99b73a19b2f";


    public static String JsapiTicket2() throws Exception {
        String requestUrl = GetPageAccessTokenUrl.replace("ACCESS_TOKEN", getAccessToken2());
        HttpClient client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(requestUrl);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = client.execute(httpget, responseHandler);
        JSONObject res = JSONObject.fromObject(response);
        String errcode = String.valueOf(res.get("errcode"));
        if (!"0".equals(errcode)) {
            throw new Exception(response);
        }
        return String.valueOf(res.get("ticket"));
    }

    public static Map<String, String> JsapiTicket() {
        String requestUrl = GetPageAccessTokenUrl.replace("ACCESS_TOKEN", getAccessToken().get("accessToken"));
        HttpClient client = null;
        Map<String, String> result = new HashMap<String, String>();
        try {
            client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(requestUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(httpget, responseHandler);
            JSONObject OpenidJSONO = JSONObject.fromObject(response);
            String errcode = String.valueOf(OpenidJSONO.get("errcode"));
            String errmsg = String.valueOf(OpenidJSONO.get("errmsg"));
            String ticket = String.valueOf(OpenidJSONO.get("ticket"));
            String expires_in = String.valueOf(OpenidJSONO.get("expires_in"));
            result.put("errcode", errcode);
            result.put("errmsg", errmsg);
            result.put("ticket", ticket);
            result.put("expires_in", expires_in);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            client.getConnectionManager().shutdown();
        }
        return result;
    }

    // 网页授权接口
    public final static String AccessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public static Map<String, String> getAccessToken() {
        String requestUrl = AccessTokenUrl.replace("APPID", APP_ID).replace("APPSECRET", APP_SECRET);
        HttpClient client = null;
        Map<String, String> result = new HashMap<>();
        String accessToken = null;
        try {
            client = new DefaultHttpClient();
            HttpGet httpget = new HttpGet(requestUrl);
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            String response = client.execute(httpget, responseHandler);
            JSONObject OpenidJSONO = JSONObject.fromObject(response);
            Object access_token = OpenidJSONO.get("access_token");
            if (access_token == null) {
                log.error("token 错误 返回数据:{}", response);
            }
            accessToken = String.valueOf(access_token);
            result.put("accessToken", accessToken);
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
        } finally {
            client.getConnectionManager().shutdown();
        }
        return result;
    }

    public static String getAccessToken2() throws Exception {
        String requestUrl = AccessTokenUrl.replace("APPID", APP_ID).replace("APPSECRET", APP_SECRET);
        HttpClient client = null;
        client = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(requestUrl);
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        String response = client.execute(httpget, responseHandler);
        JSONObject OpenidJSONO = JSONObject.fromObject(response);
        Object access_token = OpenidJSONO.get("access_token");
        if (access_token == null) {
            throw new Exception(response);
        }
        return String.valueOf(access_token);
    }
}