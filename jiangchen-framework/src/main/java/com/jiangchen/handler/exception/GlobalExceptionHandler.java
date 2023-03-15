package com.jiangchen.handler.exception;

import com.jiangchen.domain.ResponseResult;
import com.jiangchen.enums.AppHttpCodeEnum;
import com.jiangchen.exception.SystemException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 接收来自SystemException的异常
     * @param exception
     * @return
     */
    @ExceptionHandler(SystemException.class)
    public ResponseResult systemExceptionHandler(SystemException exception){
        log.error("出现了异常：{}",exception);
        return ResponseResult.errorResult(exception.getCode(),exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseResult exceptionHandler(Exception exception){
        log.error("出现了异常：{}",exception);
        return ResponseResult.errorResult(AppHttpCodeEnum.SYSTEM_ERROR.getCode(),exception.getMessage());
    }

}
