package com.jh.jsuk.utils;

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
public class JPushUtils {
    protected static final Logger LOG = LoggerFactory.getLogger(JPushUtils.class);

    //
    private static final String appKey = "bbe69dfbb95302e855056f6a";
    //
    private static final String masterSecret = "514bd7608480bf939a71e722";

    /**
     * @param alias
     * @param content
     * @param title
     * @param map
     */
    public static void pushMsg(String alias, String content, String title, Map<String, String> map) {
        JPushClient jPushClient = JPushUtils.getJPushClient();
        try {
            PushPayload push = push(alias, content, title, map);
            PushResult pushResult = jPushClient.sendPush(push);
            if (pushResult.isResultOK()) {

            }
            System.out.println("push result  :  " + pushResult.isResultOK());
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static void pushAllMsg(String content, String title, Map<String, String> map) {
        JPushClient jPushClient = JPushUtils.getJPushClient();
        try {
            PushPayload push = pushAll(content, title, map);
            PushResult pushResult = jPushClient.sendPush(push);
            if (pushResult.isResultOK()) {

            }
            System.out.println("push result  :  " + pushResult.isResultOK());
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("type", "1");
        for (int i = 0; i < 10; i++) {
            pushMsg("381", "兑换积分到账100 test" + i, "", stringStringHashMap);
        }
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
