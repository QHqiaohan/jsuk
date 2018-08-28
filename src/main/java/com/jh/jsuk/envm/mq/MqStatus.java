package com.jh.jsuk.envm.mq;

import com.github.tj123.common.enum2md.Envm;
import com.jh.jsuk.envm.BaseEnum;

@Envm(name = "mq 消息状态" ,
    description = "rabbitmq 消息状态"
)
public enum  MqStatus implements BaseEnum {

    CREATE(1,"创建"),

    SENT(2,"发送"),

    CONSUME(3,"消费");


    private final Integer key;

    private final String value;

    MqStatus(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public Integer getKey() {
        return key;
    }
}
