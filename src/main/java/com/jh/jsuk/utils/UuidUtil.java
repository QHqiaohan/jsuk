package com.jh.jsuk.utils;

import java.util.UUID;

public class UuidUtil {

    /**
     * 生成 uuid
     *
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "").toLowerCase();
    }

}
