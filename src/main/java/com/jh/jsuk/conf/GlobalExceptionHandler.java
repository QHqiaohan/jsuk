package com.jh.jsuk.conf;

import com.jh.jsuk.exception.MessageException;
import com.jh.jsuk.exception.NeedLoginException;
import com.jh.jsuk.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public Result runtimeHandler(RuntimeException e) {
        log.error(e.getLocalizedMessage(), e);
        return new Result().erro(e.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Result errorHandler(Exception e) {
        log.error(e.getLocalizedMessage(), e);
        return new Result().excption(e.getLocalizedMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = MessageException.class)
    public Result messageErrorHandler(MessageException e) {
        log.error(e.getMessage(), e);
        return new Result().erro(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = NeedLoginException.class)
    public Result needLoginErrorHandler(NeedLoginException e) {
        log.error(e.getMessage(), e);
        return new Result().noLogin();
    }
}
