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
        ThirdPayVo payVo = new ThirdPayVo();
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
        System.out.println(o.getPayVo().getPrice());
    }
}
