package com.jh.jsuk.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LuTao on 2017/8/6.
 * 第三方极光推送
 */
public class ShopJPushUtils {
    protected static final Logger LOG = LoggerFactory.getLogger(ShopJPushUtils.class);

    //
    private static final String appKey = "d945fd57fafb43048d1f0318";
    //
    private static final String masterSecret = "d7bbf38e56539cb1cd60a41a";

    /**
     *推送消息
     * @param alias
     * @param content
     * @param title
     * @param map
     * @return 消息推送成功返回true 否则返回false
     */
    public static boolean pushMsg(String alias, String content, String title, Map<String, String> map) {
        JPushClient jPushClient = ShopJPushUtils.getJPushClient();
        PushPayload push = push(alias, content, title, map);
        try {
            PushResult pushResult = jPushClient.sendPush(push);
            LOG.info("push result : " + pushResult.isResultOK());
            if (pushResult.isResultOK()) {
                return true;
            }
        } catch (APIConnectionException | APIRequestException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void pushMsgMusic(String alias, String content, String title, Map<String, String> map) {
        JPushClient jPushClient = ShopJPushUtils.getJPushClient();
        try {
            Map<String, String> map1 = new HashMap<>();
            map1.put("sound", "notify.mp3");
            PushPayload push = pushMusic(alias, content, title, map1);
            PushResult pushResult = jPushClient.sendPush(push);
            if (pushResult.isResultOK()) {

            }
            System.out.println("push result  :  " + pushResult.isResultOK());
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        //HashMap<String, String> stringStringHashMap = new HashMap<>();
        //stringStringHashMap.put("type","1");
        //for (int i = 0 ; i<10 ;i++){
        pushMsg("5", "test", "", null);
        //}
    }

    /**
     * 获取极光服务端实列
     *
     * @return
     */
    public static final JPushClient getJPushClient() {
        return new JPushClient(masterSecret, appKey);
    }


    public static PushPayload all_alert(String content) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.alert(content)).build();
    }

    /**
     * push
     *
     * @param alias
     * @param content
     * @param title
     * @param map     Extras
     * @return
     */
    public static PushPayload push(String alias, String content, String title, Map<String, String> map) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(content)
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder().setTitle(title).addExtras(map)
                                                .build())
                                .addPlatformNotification(
                                        IosNotification.newBuilder().setSound("happy").addExtras(map)
                                                .build())
                                .build())
                .setOptions(Options.newBuilder().setApnsProduction(true)
                        .build())
                .build();
    }

    public static PushPayload pushMusic(String alias, String content, String title, Map<String, String> map) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(content)
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder().setTitle(title).addExtras(map)
                                                .build())
                                .addPlatformNotification(
                                        IosNotification.newBuilder().setSound("notify.mp3").addExtras(map)
                                                .build())
                                .build())
                .setOptions(Options.newBuilder().setApnsProduction(true)
                        .build())
                .build();
    }

    public static PushPayload pushAll(String content, String title, Map<String, String> map) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(
                        Notification
                                .newBuilder()
                                .setAlert(content)
                                .addPlatformNotification(
                                        AndroidNotification.newBuilder().setTitle(title).addExtras(map)
                                                .build())
                                .addPlatformNotification(
                                        IosNotification.newBuilder().setSound("happy").addExtras(map)
                                                .build())
                                .build())
                .setOptions(Options.newBuilder().setApnsProduction(true)
                        .build())
                .build();
    }

}
