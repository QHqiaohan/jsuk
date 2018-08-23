package com.jh.jsuk.envm;

public enum  MqStatus implements BaseEnum{

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
