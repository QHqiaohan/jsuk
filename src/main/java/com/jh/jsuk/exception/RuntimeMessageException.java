package com.jh.jsuk.exception;

/**
 * 直接抛出来，前端展示
 */
public class RuntimeMessageException extends RuntimeException {

    public RuntimeMessageException(String message) {
        super(message);
    }
}
