package com.jh.jsuk.conf;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by hasee on 2017/6/1.
 */
public class JsonResult {
    private String code;
    private String msg;
    private Map<String, Object> data = Maps.newHashMap();

    public JsonResult(String code, String msg, Map<String, Object> data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public JsonResult() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }
}
