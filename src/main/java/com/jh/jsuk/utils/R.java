package com.jh.jsuk.utils;

import cn.hutool.core.util.StrUtil;
import com.jh.jsuk.conf.Constant;

public class R extends Result {

    public static R create() {
        return new R();
    }

    public R er(String msg) {
        setCode(Constant.RESCODE_ERROR);
        setMsg(msg);
        return this;
    }

    public R er(String msg, Object... value) {
        er(StrUtil.format(msg, value));
        return this;
    }

    public R suc(String msg) {
        setCode(Constant.RESCODE_SUCCESS);
        setMsg(msg);
        return this;
    }

    public R suc() {
        setCode(Constant.RESCODE_SUCCESS);
        setMsg("");
        return this;
    }

    public static R create(String errMsg) {
        R r = new R();
        r.er(errMsg);
        return r;
    }

    public static R succ(Object obj) {
        R r = new R();
        r.success(obj);
        return r;
    }

    public static R succ() {
        R r = new R();
        r.suc();
        return r;
    }

    public static R err(long code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static R err(String msg) {
        R r = new R();
        r.er(msg);
        return r;
    }


}
