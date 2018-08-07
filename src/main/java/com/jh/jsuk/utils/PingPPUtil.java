package com.jh.jsuk.utils;

import com.jh.jsuk.entity.ShopGoods;
import com.jh.jsuk.entity.User;
import com.jh.jsuk.entity.UserOrder;
import com.jh.jsuk.envm.PayType;
import com.pingplusplus.Pingpp;
import com.pingplusplus.exception.*;
import com.pingplusplus.model.Charge;

import java.math.BigDecimal;
import java.util.*;

/**
 * Author: xyl
 * Date:2018/7/24 16:09
 * Description: ping++
 */
public class PingPPUtil {
    private final static String appId = "app_HGSirL9a90uHrfvr";

    private static void init() {
        // 设置 API Key
        Pingpp.apiKey = "sk_test_0aHKuT4CubjHuLCif5mDizLS";

        // 设置私钥路径，用于请求签名
//        Pingpp.privateKeyPath = privateKeyFilePath;

        /**
         * 或者直接设置私钥内容
         Pingpp.privateKey = "-----BEGIN RSA PRIVATE KEY-----\n" +
         "... 私钥内容字符串 ...\n" +
         "-----END RSA PRIVATE KEY-----\n";
         */
        /*Pingpp.privateKey = "-----BEGIN PUBLIC KEY-----\n" +
                "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0AmdRdCNIrrMc1v8Z6wX\n" +
                "ozLzfDQThW0S/Rj03yYSuRxZDPHBdmYHtjNlAAzxbpZfeYBySrr2Q90zdEbscBUV\n" +
                "lvW0Hs0QOW91xhnaEj7xneehbkJ1+gwr7yl6qoN+GDubpULZUZDSqPLSz+8WGhqy\n" +
                "lg4iT3+JzcHSD2YN6TAq3msEoB7NH0QVmzDGOjY48+v9UKVfCWtTriFaGxcnUnyE\n" +
                "3ckFG4/aNNCFgZPT+D0s2R9JmNjSxcN/nt/BGJZ1Q1bUStIPKhtvw9tx8cDToUSK\n" +
                "B0O+LYlRQAQscpGRMM9za3+ebcOe8U587rstB4DfHDwGXvvB7KIS8qRzWTFquR6u\n" +
                "8wIDAQAB\n" +
                "-----END PUBLIC KEY-----";*/
    }

    /**
     * 创建 Charge
     * <p>
     * 创建 Charge 用户需要组装一个 map 对象作为参数传递给 Charge.create();
     * map 里面参数的具体说明请参考：https://www.pingxx.com/api#api-c-new
     *
     * @return Charge
     */
    public static Charge createCharge(UserOrder userOrder, User user, ShopGoods shopGoods) {
        init();
        Charge charge = null;
        String channel = getChannel(userOrder);
        Map<String, Object> chargeMap = new HashMap<>();
        chargeMap.put("amount", userOrder.getOrderRealPrice().multiply(new BigDecimal("100")));//订单总金额, 人民币单位：分（如订单总金额为 1 元，此处请填 100）
        chargeMap.put("currency", "cny");//人民币
        chargeMap.put("subject", shopGoods.getGoodsName()); //商品标题
        chargeMap.put("body", shopGoods.getGoodsName());
        chargeMap.put("order_no", userOrder.getOrderNum());// 推荐使用 8-20 位，要求数字或字母，不允许其他字符
        chargeMap.put("channel", channel);// 支付使用的第三方支付渠道取值，请参考：https://www.pingxx.com/api#api-c-new
        chargeMap.put("client_ip", user.getLoginIp()); // 发起支付请求客户端的 IP 地址，格式为 IPV4，如: 127.0.0.1
        Map<String, String> app = new HashMap<>();
        app.put("id", appId);
        chargeMap.put("app", app);

        // extra 取值请查看相应方法说明
        chargeMap.put("extra", channelExtra(channel));

        try {
            //发起交易请求
            charge = Charge.create(chargeMap);
            // 传到客户端请先转成字符串 .toString(), 调该方法，会自动转成正确的 JSON 字符串
            String chargeString = charge.toString();
            System.out.println(chargeString);
        } catch (APIConnectionException | ChannelException | AuthenticationException | RateLimitException | APIException | InvalidRequestException e) {
            e.printStackTrace();
        }
        return charge;
    }

    private static Map<String, Object> channelExtra(String channel) {
        Map<String, Object> extra = new HashMap<>();

        switch (channel) {
            case "alipay":
                extra = alipayExtra();
                break;
            case "wx":
                extra = wxExtra();
                break;
            case "upacp":
                extra = upacpExtra();
                break;
        }
        return extra;
    }

    // extra 根据渠道会有不同的参数

    private static Map<String, Object> alipayExtra() {
        Map<String, Object> extra = new HashMap<>();

        // 可选，开放平台返回的包含账户信息的 token（授权令牌，商户在一定时间内对支付宝某些服务的访问权限）。通过授权登录后获取的  alipay_open_id ，作为该参数的  value ，登录授权账户即会为支付账户，32 位字符串。
        // extra.put("extern_token", "TOKEN");

        // 可选，是否发起实名校验，T 代表发起实名校验；F 代表不发起实名校验。
        extra.put("rn_check", "T");

        return extra;
    }

    private static Map<String, Object> wxExtra() {
        Map<String, Object> extra = new HashMap<>();
        // 可选，指定支付方式，指定不能使用信用卡支付可设置为 no_credit 。
        extra.put("limit_pay", "no_credit");

        // 可选，商品标记，代金券或立减优惠功能的参数。
        // extra.put("goods_tag", "YOUR_GOODS_TAG");
        return extra;
    }

    private static Map<String, Object> upacpExtra() {
        Map<String, Object> extra = new HashMap<>();

        return extra;
    }

    private static String getChannel(UserOrder userOrder) {
        switch (userOrder.getPayType()) {
            //支付宝
            case 2:
                return "alipay";
            //微信
            case 3:
                return "wx";
            //银行卡
            case 4:
                return "upacp";
        }
        return null;
    }
}
