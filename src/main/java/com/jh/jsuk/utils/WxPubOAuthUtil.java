package com.jh.jsuk.utils;

import com.pingplusplus.exception.ChannelException;
import com.pingplusplus.util.WxpubOAuth;

import java.io.UnsupportedEncodingException;

/**
 * Author:xyl
 * Date:2018/8/8 22:05
 * Description:公众号支付时 获取open_id
 * <p>
 * 开发者需要填写 apiKey 、appId 、url 和 openid 。
 * <p>
 * apiKey 有 TestKey 和 LiveKey 两种。
 * <p>
 * TestKey 模式下不会产生真实的交易。
 * <p>
 * openid 是发送红包的对象在公共平台下的 openid ,获得 openid 的方法可以参考微信文档：http://mp.weixin.qq.com/wiki/17/c0f37d5704f0b64713d5d2c37b468d75.html
 */
public class WxPubOAuthUtil {
    /**
     * Ping++ 管理平台对应的应用 ID，app_id 获取方式：登录 [Dashboard](https://dashboard.pingxx.com)->点击你创建的应用->应用首页->应用 ID(App ID)
     */
    private static String appId;

    /**
     * 微信公共号里面获取 openid 时的回跳 URL
     */
    public static String redirectUrl = "http://www.h26i5.cn/wechat/";
    /**
     * 微信公共号的 appId，通常为 `wx` 开头的字符串
     */
    public static String wxAppId = "wx5c8b89838ce96f7e";
    /**
     * 微信公共号的 secret
     */
    public static String wxAppSecret = "a4a07ee1f5e89eb40429e99b73a19b2f";

    /*public static void runDemos(String appId) throws UnsupportedEncodingException {
        WxPubOAuthUtil.appId = appId;
        getOpenid();
        System.out.println("------- 如果要是用微信的 jsapi 并且要启用签名, 请参考以下方法 -------");
    }*/

    public static String getOpenid(String code) throws UnsupportedEncodingException, ChannelException {
//        System.out.println("1. 你需要有一个处理回跳的 URL");
//            redirectUrl = "http://用于处理回跳的URL";
//        String url = WxpubOAuth.createOauthUrlForCode(wxAppId, redirectUrl, false);
      /*  System.out.println("2. 跳转到该 URL");
        System.out.println(url);*/
        return WxpubOAuth.getOpenId(wxAppId, wxAppSecret, code);
    }

    public static String getOpenidWithCode(String url) throws UnsupportedEncodingException, ChannelException {
        System.out.println("3. 微信内置浏览器会带上参数 code 跳转到你传的地址: " + redirectUrl + "?code=os823ndskelcncfyfms");
        // 获取 URL 中的 code 参数
        String[] strings = url.split("=");
        String code = strings[strings.length - 1];
        System.out.println("url===" + url);
        System.out.println("code ====" + code);
        return WxpubOAuth.getOpenId(wxAppId, wxAppSecret, code);
    }
}
