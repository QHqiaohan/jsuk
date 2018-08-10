package com.jh.jsuk.utils;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import com.jh.jsuk.envm.UserType;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by LuTao on 2017/8/6.
 * 第三方极光推送
 */
@Slf4j
public class JPushUtils {

//    protected static final Logger LOG = LoggerFactory.getLogger(JPushUtils.class);

    //
//    private static final String appKey = "bbe69dfbb95302e855056f6a";
    //
//    private static final String masterSecret = "514bd7608480bf939a71e722";

    private static final String USER_APP_KEY = "75169e06c10509c2e10e9f2f";
    private static final String USER_MASTER_SECRET = "dcb89d137df28426363163a6";

    private static final String SHOP_APP_KEY = "e25d191e8bbafc2c90e58217";
    private static final String SHOP_MASTER_SECRET = "2933fe2cf982884d239174c1";

    private static final String DISTB_APP_KEY = "d5bed28b75d78bb787e1268a";
    private static final String DISTB_MASTER_SECRET = "0c24e7eb470864fa09783fc4";

//    /**
//     * @param alias
//     * @param content
//     * @param title
//     * @param map
//     */
//    public static void pushMsg(String alias, String content, String title, Map<String, String> map) {
//        JPushClient jPushClient = JPushUtils.getJPushClient();
//        try {
//            PushPayload push = push(alias, content, title, map);
//            PushResult pushResult = jPushClient.sendPush(push);
//            if (pushResult.isResultOK()) {
//
//            }
//            System.out.println("push result  :  " + pushResult.isResultOK());
//        } catch (Exception e) {
//            //e.printStackTrace();
//        }
//    }
//
//    public static void pushAllMsg(String content, String title, Map<String, String> map) {
//        JPushClient jPushClient = JPushUtils.getJPushClient();
//        try {
//            PushPayload push = pushAll(content, title, map);
//            PushResult pushResult = jPushClient.sendPush(push);
//            if (pushResult.isResultOK()) {
//
//            }
//            System.out.println("push result  :  " + pushResult.isResultOK());
//        } catch (Exception e) {
//            //e.printStackTrace();
//        }
//    }

//    public static void main(String[] args) {
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("type", "1");
//        for (int i = 0; i < 10; i++) {
//            pushMsg("381", "兑换积分到账100 test" + i, "", stringStringHashMap);
//        }
//    }


    public static JPushClient userClient() {
        return new JPushClient(USER_MASTER_SECRET, USER_APP_KEY);
    }

    public static JPushClient shopClient() {
        return new JPushClient(SHOP_MASTER_SECRET, SHOP_APP_KEY);
    }

    public static JPushClient distpClient() {
        return new JPushClient(DISTB_MASTER_SECRET, DISTB_APP_KEY);
    }


    public static void push(UserType userType, String userId, String content, String title) throws Exception {
        JPushClient client = null;
        switch (userType) {
            case SHOP:
                client = shopClient();
                break;
            case DISTRIBUTION:
                client = distpClient();
                break;
            case USER:
                client = userClient();
                break;
            default:
            case ADMIN:
                log.error("不支持的用户类型: {}", userType);
                return;
        }
        try {
            client.sendPush(buildPush(userId, content, title, new HashMap<>()));
        } catch (APIConnectionException e) {
            log.error(e.getMessage());
            throw e;
        } catch (APIRequestException e) {
            log.error(e.getMessage());
            throw e;
        }
    }

//    public static PushPayload all_alert(String content) {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.all())
//                .setNotification(Notification.alert(content)).build();
//    }


    public static PushPayload buildPush(String alias, String content, String title, Map<String, String> map) {
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

//    public static PushPayload pushAll(String content, String title, Map<String, String> map) {
//        return PushPayload.newBuilder()
//                .setPlatform(Platform.all())
//                .setAudience(Audience.all())
//                .setNotification(
//                        Notification
//                                .newBuilder()
//                                .setAlert(content)
//                                .addPlatformNotification(
//                                        AndroidNotification.newBuilder().setTitle(title).addExtras(map)
//                                                .build())
//                                .addPlatformNotification(
//                                        IosNotification.newBuilder().setSound("happy").addExtras(map)
//                                                .build())
//                                .build())
//                .setOptions(Options.newBuilder().setApnsProduction(true)
//                        .build())
//                .build();
//    }

//    /**
//     * 客户端 给所有平台的所有用户发消息
//     */
//    public static void sendAllMessage(String alert) {
//        JPushClient jpushClient = new JPushClient(Master_Secret, AppKey, null, ClientConfig.getInstance());
//        PushPayload payload = send_all_all_Message(alert);
//        try {
//            PushResult result = jpushClient.sendPush(payload);
//            log.debug(result.toString());
//        } catch (APIConnectionException e) {
//            log.error("APIConnectionException",e);
//        } catch (APIRequestException e) {
//            log.error(e.getMessage(),e);
//            log.error("HTTP Status: " + e.getStatus());
//            log.error("Error Code: " + e.getErrorCode());
//            log.error("Error Message: " + e.getErrorMessage());
//            log.error("Msg ID: " + e.getMsgId());
//        }
//    }
//
//    /**
//     * 客户端 给所有平台的所有用户发消息
//     */
//    public static void sendAllMessage(String alert,String message) {
//        JPushClient jpushClient = new JPushClient(Master_Secret, AppKey, null, ClientConfig.getInstance());
//        PushPayload payload = send_all_all_Message(alert,message);
//        try {
//            PushResult result = jpushClient.sendPush(payload);
//            log.debug(result.toString());
//        } catch (APIConnectionException e) {
//            log.error("APIConnectionException",e);
//        } catch (APIRequestException e) {
//            log.error(e.getMessage(),e);
//            log.error("HTTP Status: " + e.getStatus());
//            //sdfsdfs
//            log.error("Error Code: " + e.getErrorCode());
//            log.error("Error Message: " + e.getErrorMessage());
//            log.error("Msg ID: " + e.getMsgId());
//        }
//    }
//
//    /**
//     * 客户端 给所有平台的一个或者一组用户发送信息
//     */
//    public static void sendAllMessage(String alert, List<String> aliasList) {
//
//        JPushClient jpushClient = new JPushClient(Master_Secret, AppKey);
//        PushPayload payload = send_all_all_Message(alert, aliasList);
//        try {
//            PushResult result = jpushClient.sendPush(payload);
//            log.debug(result.toString());
//        } catch (APIConnectionException e) {
//            log.error("APIConnectionException",e);
//        } catch (APIRequestException e) {
//            log.error(e.getMessage(),e);
//            log.error("HTTP Status: " + e.getStatus());
//            log.error("Error Code: " + e.getErrorCode());
//            log.error("Error Message: " + e.getErrorMessage());
//            log.error("Msg ID: " + e.getMsgId());
//        }
//    }
//
//    /**
//     * 极光推送：发送透传消息
//     *
//     */
//    private static PushPayload send_all_all_Message(String alert) {
//        // 全平台，所有客户端
//        return PushPayload.newBuilder()
//            // 设置平台
//            .setPlatform(Platform.all())
//            // 设置用户
//            .setAudience(Audience.all())
//            // 发送通知
//            .setNotification(Notification.alert(alert))
//            // true为生产环境，false为开发环境
//            .setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
//    }
//
//    /**
//     * 极光推送：发送透传消息
//     */
//    private static PushPayload send_all_all_Message(String alert,String message) {
//        // 全平台，所有客户端
//        return PushPayload.newBuilder()
//            // 设置平台
//            .setPlatform(Platform.all())
//            // 设置用户
//            .setAudience(Audience.all())
//            // 发送通知
//            .setNotification(Notification.alert(alert))
//            // 推送内容
//            .setMessage(Message.content(message))
//            // true为生产环境，false为开发环境
//            .setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
//    }
//
//
//    /**
//     * 极光推送：生成向一个或者一组用户发送的消息
//     * allPlatformAndAlias:
//     *
//     * @author JL
//     * @param alert
//     * @param extras
//     * @param aliasList
//     * @return
//     */
//    private static PushPayload send_all_all_Message(String message,List<String> aliasList) {
//        // 全平台，所有客户端
//        return PushPayload.newBuilder()
//            // 设置平台
//            .setPlatform(Platform.all())
//            // 设置用户
//            .setAudience(Audience.registrationId(aliasList))
//            // 发送通知
//            .setNotification(Notification.alert(message))
//            // true为生产环境，false为开发环境
//            .setOptions(Options.newBuilder().setApnsProduction(true).build()).build();
//    }


}
