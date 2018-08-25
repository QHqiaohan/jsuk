package com.jh.jsuk;

import com.jh.jsuk.entity.vo.ThirdPayVo;
import com.jh.jsuk.entity.vo.ThirdPayVoChild;
import net.sf.json.JSONObject;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author:xyl
 * Date:2018/8/11 13:59
 * Description:
 */
public class JsonTest {

    @Test
    public void test(){
        System.out.println("com.jh.jsuk.controller.UserController".replaceAll("^[\\w\\d_\\.]+\\.",""));
        Pattern compile = Pattern.compile("\\w+$");
//        Matcher matcher = compile.matcher("com.jh.jsuk.controller.UserController");
        Matcher matcher = compile.matcher("");
        System.out.println(matcher.find());
        System.out.println(matcher.group());
//        "com.jh.jsuk.controller.UserController"
    }

    public static void main(String[] args) {
       /* ThirdPayVo payVo = new ThirdPayVo();
        payVo.setParam("22");
        payVo.setPayType(1);
        payVo.setPrice("33");
        payVo.setUserId(123123);
        System.out.println(JSONObject.fromObject(payVo).toString());
        ThirdPayVoChild child = new ThirdPayVoChild();
        child.setPayVo(payVo);
        child.setUserRechargeRecordId(123);
        child.setUserRemainderId(11);
        String s = JSONObject.fromObject(child).toString();
        System.out.println(s);
        ThirdPayVoChild o = (ThirdPayVoChild) JSONObject.toBean(JSONObject.fromObject(s), ThirdPayVoChild.class);
        System.out.println(o.getPayVo().getPrice());*/

        String string = "{\n" +
            "  \"id\": \"ch_mbvnn91Oa5u9a5yPO0K8uTmP\",\n" +
            "  \"object\": \"charge\",\n" +
            "  \"created\": 1534148835,\n" +
            "  \"livemode\": true,\n" +
            "  \"paid\": false,\n" +
            "  \"refunded\": false,\n" +
            "  \"reversed\": false,\n" +
            "  \"app\": \"app_HGSirL9a90uHrfvr\",\n" +
            "  \"channel\": \"alipay\",\n" +
            "  \"orderNo\": \"1000001257889041\",\n" +
            "  \"clientIp\": \"171.221.148.88\",\n" +
            "  \"amount\": 1,\n" +
            "  \"amountSettle\": 1,\n" +
            "  \"currency\": \"cny\",\n" +
            "  \"subject\": \"快递跑腿\",\n" +
            "  \"body\": \"{\\\"userRechargeRecordId\\\":0,\\\"payVo\\\":{\\\"payType\\\":2,\\\"param\\\":\\\"125\\\",\\\"price\\\":\\\"65.0\\\",\\\"type\\\":4,\\\"userId\\\":241},\\\"userRemainderId\\\":0}\",\n" +
            "  \"timePaid\": null,\n" +
            "  \"timeExpire\": 1534235235,\n" +
            "  \"timeSettle\": null,\n" +
            "  \"transactionNo\": null,\n" +
            "  \"refunds\": {\n" +
            "    \"object\": \"list\",\n" +
            "    \"url\": \"/v1/charges/ch_mbvnn91Oa5u9a5yPO0K8uTmP/refunds\",\n" +
            "    \"hasMore\": false,\n" +
            "    \"data\": [\n" +
            "      \n" +
            "    ]\n" +
            "  },\n" +
            "  \"amountRefunded\": 0,\n" +
            "  \"failureCode\": null,\n" +
            "  \"failureMsg\": null,\n" +
            "  \"metadata\": {\n" +
            "    \n" +
            "  },\n" +
            "  \"credential\": {\n" +
            "    \"object\": \"credential\",\n" +
            "    \"alipay\": {\n" +
            "      \"orderInfo\": \"app_id=2018053060294328&method=alipay.trade.app.pay&format=JSON&charset=utf-8&sign_type=RSA2&timestamp=2018-08-13%2016%3A27%3A15&version=1.0&biz_content=%7B%22body%22%3A%22%7B%5C%22userRechargeRecordId%5C%22%3A0%2C%5C%22payVo%5C%22%3A%7B%5C%22payType%5C%22%3A2%2C%5C%22param%5C%22%3A%5C%22125%5C%22%2C%5C%22price%5C%22%3A%5C%2265.0%5C%22%2C%5C%22type%5C%22%3A4%2C%5C%22userId%5C%22%3A241%7D%2C%5C%22userRemainderId%5C%22%3A0%7D%22%2C%22subject%22%3A%22%E5%BF%AB%E9%80%92%E8%B7%91%E8%85%BF%22%2C%22out_trade_no%22%3A%221000001257889041%22%2C%22total_amount%22%3A0.01%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22timeout_express%22%3A%221440m%22%7D&notify_url=https%3A%2F%2Fnotify.pingxx.com%2Fnotify%2Fcharges%2Fch_mbvnn91Oa5u9a5yPO0K8uTmP&sign=Cc%2BwbwW2HHa8BL3iet77Nss4SoSKFpuAy2XOxoIUUybA8pNIGNWhF%2F4vKC0raYBPofuJXwj1Xt9YnnbRPeMxTddDg%2FluTeZuj5LpH7BUlfhLGnts%2FcD%2FE%2BKMkEGgA7OxhJR1wSDQwhhx4pX4zvU2TyM81Y29mVNimbbWh3b7JssWUShtiJsIJ5FZnTXrhUWzugUX5K7e3OxJp4dfhXvFVifEJDNHdK%2BMZWGq0x%2B0KybrRkOq0P0XlREDJ70q%2BoL4sa4ZNA9MmcmJQTFm1JvuagRKo6%2BpBTxEYwxkppSNM%2FucW1M8eT0WdXVVkgFxLlG09ufKTv14GTGu2MLCF9WYPA%3D%3D\"\n" +
            "    }\n" +
            "  },\n" +
            "  \"extra\": {\n" +
            "    \"rn_check\": \"T\"\n" +
            "  },\n" +
            "  \"description\": null\n" +
            "}";
        StringBuffer stringBuffer = new StringBuffer(string);
        JSONObject jsonObject = JSONObject.fromObject(stringBuffer.toString());
//        System.out.println(jsonObject);
        JSONObject body = JSONObject.fromObject(jsonObject.get("body"));
        System.out.println(body);
        Map map = new HashMap<String, Object>();
        map.put("payVo", ThirdPayVo.class);
        ThirdPayVoChild payVoChild = (ThirdPayVoChild) JSONObject.toBean(body, ThirdPayVoChild.class, map);
        System.err.println(payVoChild);
    }
}
