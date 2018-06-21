package com.jh.jsuk.exception;

/**
 * 直接抛出来，前端展示
 */
public class MessageException extends Exception {

    public MessageException(String message) {
        super(message);
    }
}
