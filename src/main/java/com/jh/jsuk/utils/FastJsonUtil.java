package com.jh.jsuk.utils;

import net.sf.json.JSONObject;

import java.util.Map;

/**
 * @author xieshihao
 * @date 2018-04-11 22:43
 */
public class FastJsonUtil {
    public static String toJson(Map<String, String[]> map) {
        JSONObject jsonObject = JSONObject.fromObject(map);
        String result = jsonObject.toString();
        return result;
    }
}
