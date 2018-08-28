package com.jh.jsuk.envm;


import com.github.tj123.common.enum2md.Envm;
import lombok.Getter;

@Getter
@Envm(name = "提交订单状态")
public enum OrderResponseStatus {

    SUCCESS(200, "成功"),

    PARTLY_SUCCESS(201,"部分成功"),

    TIME_OUT(203,"超时"),

    UNDER_STOCK(204,"库存不足");

    private final int key;
    private  final  String value;


    OrderResponseStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }
}
