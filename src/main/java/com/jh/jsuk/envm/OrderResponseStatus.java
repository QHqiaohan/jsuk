package com.jh.jsuk.envm;


import lombok.Getter;

@Getter
public enum OrderResponseStatus {

    SUCCESS(200, "成功"),

    PARTLY_SUCCESS(201,"部分成功"),

    FAILED(203,"失败");

    private final int key;
    private  final  String value;


    OrderResponseStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
