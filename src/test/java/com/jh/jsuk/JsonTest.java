package com.jh.jsuk;

import com.jh.jsuk.entity.vo.ThirdPayVo;
import com.jh.jsuk.entity.vo.ThirdPayVoChild;
import net.sf.json.JSONObject;

/**
 * Author:xyl
 * Date:2018/8/11 13:59
 * Description:
 */
public class JsonTest {
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

        String string = "{\"id\":\"evt_401180811181943410046602\",\"created\":1533982782,\"livemode\":true,\"type\":\"charge.succeeded\",\"data\":{\"object\":{\"id\":\"ch_S40eD8ej14u5zvDSOKPC8iXD\",\"object\":\"charge\",\"created\":1533982775,\"livemode\":true,\"paid\":true,\"refunded\":false,\"reversed\":false,\"app\":\"app_HGSirL9a90uHrfvr\",\"channel\":\"wx_pub\",\"order_no\":\"1000001295075353\",\"client_ip\":\"117.136.70.44\",\"amount\":1,\"amount_settle\":1,\"currency\":\"cny\",\"subject\":\"土豆\",\"body\":\"土豆\",\"extra\":{\"open_id\":\"oo-20t5MwSDSCWDB7Xn_10S7hJGY\",\"limit_pay\":\"no_credit\",\"bank_type\":\"CFT\",\"cash_fee\":\"1\",\"is_subscribe\":\"Y\"},\"time_paid\":1533982781,\"time_expire\":1533989975,\"time_settle\":null,\"transaction_no\":\"4200000152201808112296767950\",\"refunds\":{\"object\":\"list\",\"url\":\"/v1/charges/ch_S40eD8ej14u5zvDSOKPC8iXD/refunds\",\"has_more\":false,\"data\":[]},\"amount_refunded\":0,\"failure_code\":null,\"failure_msg\":null,\"metadata\":{},\"credential\":{},\"description\":null}},\"object\":\"event\",\"request\":\"iar_1WzjXDCSWDO8u9ybn14mfTaH\",\"pending_webhooks\":0}\n";
        StringBuffer stringBuffer = new StringBuffer(string);
        JSONObject jsonObject = JSONObject.fromObject(stringBuffer.toString());
        System.out.println(jsonObject);
        JSONObject data = JSONObject.fromObject(jsonObject.get("data"));
        JSONObject object = JSONObject.fromObject(data.get("object"));
//        JSONObject body = JSONObject.fromObject(object.get("body"));
        System.out.println(object);
    }
}
