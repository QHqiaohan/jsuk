package com.jh.jsuk.utils;


import com.jh.jsuk.conf.Constant;

public class Result<T> {
    private Long code;
    private String msg;
    private T data;

    public Result() {
    }

    public Result(Long code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result erro(String msg) {
        this.code = Constant.RESCODE_ERROR;
        this.msg = msg;
        return this;
    }

    public Result erro(String msg, T data) {
        this.code = Constant.RESCODE_ERROR;
        this.msg = msg;
        this.data = data;
        return this;
    }

//    public Result excption(String msg) {
//        this.code = Constant.RESCODE_EXCEPTION;
//        this.msg = msg;
//        return this;
//    }

//    public Result nofound() {
//        this.code = Constant.RESCODE_NOFOUND;
//        this.msg = "没有找到该用户";
//        return this;
//    }

    public Result overdue() {
        this.code = Constant.RESCODE_NOLOGIN;
        this.msg = "登陆过期";
        return this;
    }

    public Result noLogin() {
        this.code = Constant.RESCODE_NOLOGIN;
        this.msg = "您还没有登陆";
        return this;
    }

    public Result success(String msg, T data) {
        this.code = Constant.RESCODE_SUCCESS;
        this.msg = msg;
        this.data = data;
        return this;
    }

    public Result success(T data) {
        this.code = Constant.RESCODE_SUCCESS;
        this.msg = "success";
        this.data = data;
        return this;
    }

    public Result success(String msg) {
        this.code = Constant.RESCODE_SUCCESS;
        this.msg = msg;
        return this;
    }

    public Result success() {
        this.code = Constant.RESCODE_SUCCESS;
        this.msg = "success";
        return this;
    }

    public Long getCode() {
        return code;
    }

    public Result<T> setCode(Long code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result<T> setData(T data) {
        this.data = data;
        return this;
    }


    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
