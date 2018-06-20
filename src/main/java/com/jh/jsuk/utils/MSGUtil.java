package com.jh.jsuk.utils;

/**
 * Created by lyq on 2017/7/3.
 */
public class MSGUtil {
    public final static Integer OK = 200;
    public final static Integer ERRO = 500;
    private String msg;
    private Integer state;
    private Object data;

    public MSGUtil() {
    }

    ;

    public MSGUtil(Integer state, Object data) {
        this.msg = "";
        this.data = data;
        this.state = state;
    }

    public MSGUtil(Integer state, Object data, String msg) {
        this.msg = msg;
        this.data = data;
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
