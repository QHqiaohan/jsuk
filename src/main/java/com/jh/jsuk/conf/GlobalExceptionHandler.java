package com.jh.jsuk.conf;

import com.jh.jsuk.utils.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public Result runtimeHandler(RuntimeException e) {
        e.printStackTrace();
        return new Result().erro(e.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception e) {
        e.printStackTrace();
        return new Result().excption(e.getLocalizedMessage());
    }
}
