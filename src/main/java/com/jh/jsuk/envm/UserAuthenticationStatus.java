package com.jh.jsuk.envm;

import com.jh.jsuk.common.Envm;
import lombok.Getter;


//1=通过 0=审核中 -1=未通过 -2=未认证
@Getter
@Envm(name = "用户认证状态")
public enum UserAuthenticationStatus {

    UNDER_AUTH(0,"审核中"),

    PASSED(1,"通过"),

    NOT_PASS(2,"未通过");

    private final int key;

    private final String value;

    UserAuthenticationStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
