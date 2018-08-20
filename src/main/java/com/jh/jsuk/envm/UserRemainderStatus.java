package com.jh.jsuk.envm;

import com.jh.jsuk.common.Envm;
import lombok.Getter;

@Getter
@Envm(name = "用户余额状态")
public enum UserRemainderStatus implements BaseEnum{

    APPLYING(1,"申请中"),

    PASSED(2,"通过"),

    REFUSED(3,"拒绝");

    private final Integer key;
    private final String value;

    UserRemainderStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
}
