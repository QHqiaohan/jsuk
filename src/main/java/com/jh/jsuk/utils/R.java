package com.jh.jsuk.utils;

public class R extends Result {

    public static R succ(Object obj) {
        R r = new R();
        r.success(obj);
        return r;
    }

    public static R succ() {
        R r = new R();
        r.success();
        return r;
    }

    public static R err(long code, String msg) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg);
        return r;
    }

    public static R err(String msg) {
        return R.err(-11,msg);
    }

    public static R notFound(){
        R r = new R();
        r.nofound();
        return r;
    }


}
