package com.jh.jsuk.utils;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
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
public class DisJPushUtils {
    protected static final Logger LOG = LoggerFactory.getLogger(DisJPushUtils.class);

    //
    private static final String appKey = "22ac26a3da54b67df5648809";
    //
    private static final String masterSecret = "13300076bf0a18ff04e048b7";

    /**
     * @param alias
     * @param content
     * @param title
     * @param map
     */
    public static void pushMsg(String alias, String content, String title, Map<String, String> map) {
        JPushClient jPushClient = DisJPushUtils.getJPushClient();
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
        JPushClient jPushClient = DisJPushUtils.getJPushClient();
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


    /**
     * 发送给所有用户
     *
     * @param content     通知内容标题
     * @param title       消息内容标题
     * @param msg_content 消息内容
     * @param extrasparam 扩展字段
     * @return 0推送失败，1推送成功
     */
    public static int sendToAll(String content, String title, String msg_content, String extrasparam) {
        int result = 0;
        try {
            PushPayload pushPayload = DisJPushUtils.buildPushObject_android_and_ios(content, title, msg_content, extrasparam);
            System.out.println(pushPayload);
            PushResult pushResult = DisJPushUtils.getJPushClient().sendPush(pushPayload);
            System.out.println(pushResult);
            if (pushResult.getResponseCode() == 200) {
                result = 1;
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    public static PushPayload buildPushObject_android_and_ios(String notification_title, String msg_title, String msg_content, String extrasparam) {
        return PushPayload.newBuilder()

                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                        .setAlert(notification_title)
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setAlert(notification_title)
                                .setTitle(msg_title)
                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                //.addExtra("androidNotification extras key", extrasparam)
                                .addExtra("sound", "notify.mp3")
                                .build()
                        )
                        .addPlatformNotification(IosNotification.newBuilder()
                                //传一个IosAlert对象，指定apns title、title、subtitle等
                                .setAlert(notification_title)
                                //直接传alert
                                //此项是指定此推送的badge自动加1
                                .incrBadge(1)
                                //此字段的值default表示系统默认声音；传sound.caf表示此推送以项目里面打包的sound.caf声音来提醒，
                                // 如果系统没有此音频则以系统默认声音提醒；此字段如果传空字符串，iOS9及以上的系统是无声音提醒，以下的系统是默认声音
                                .setSound("notify.mp3")

                                //此字段为透传字段，不会显示在通知栏。用户可以通过此字段来做一些定制需求，如特定的key传要指定跳转的页面（value）
                                .addExtra("iosNotification extras key", extrasparam)
                                //此项说明此推送是一个background推送，想了解background看：http://docs.jpush.io/client/ios_tutorials/#ios-7-background-remote-notification
                                // .setContentAvailable(true)

                                .build()
                        )
                        .build()
                )
                //Platform指定了哪些平台就会像指定平台中符合推送条件的设备进行推送。 jpush的自定义消息，
                // sdk默认不做任何处理，不会有通知提示。建议看文档http://docs.jpush.io/guideline/faq/的
                // [通知与自定义消息有什么区别？]了解通知和自定义消息的区别
                .setMessage(Message.newBuilder()
                        .setMsgContent(msg_content)
                        .setTitle(msg_title)
                        .addExtra("message extras key", extrasparam)
                        .build())

                .setOptions(Options.newBuilder()
                        //此字段的值是用来指定本推送要推送的apns环境，false表示开发，true表示生产；对android和自定义消息无意义
                        .setApnsProduction(true)
                        //此字段是给开发者自己给推送编号，方便推送者分辨推送记录
                        .setSendno(1)
                        //此字段的值是用来指定本推送的离线保存时长，如果不传此字段则默认保存一天，最多指定保留十天，单位为秒
                        .setTimeToLive(86400)
                        .build()
                )
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
                                        IosNotification.newBuilder().setSound("notify.mp3").addExtras(map)
                                                .build())
                                .build())
                .setOptions(Options.newBuilder().setApnsProduction(true)
                        .build())
                .build();
    }

}
