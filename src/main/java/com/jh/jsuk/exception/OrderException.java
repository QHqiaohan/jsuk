package com.jh.jsuk.exception;

import lombok.Getter;

@Getter
public class OrderException extends Exception {


    @Getter
    public enum ExceptionType {

        UNDER_STOCK(301, "库存不足");


        private final int code;

        private final String value;

        ExceptionType(int code, String value) {
            this.code = code;
            this.value = value;
        }
    }

    private int code;

    public OrderException(ExceptionType exceptionType) {
        super(exceptionType.getValue());
        this.code = exceptionType.getCode();
    }
}
