package com.weirdo.usercenter.exception;

import com.weirdo.usercenter.common.BaseResponse;
import com.weirdo.usercenter.common.ErrorCode;
import com.weirdo.usercenter.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author weirdo
 */
@RestControllerAdvice  // 使用 @RestControllerAdvice 注解标识为全局异常处理器
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.class) // 使用 @ExceptionHandler 注解标识了两个异常处理方法：
    public BaseResponse businessExceptionHandler(BusinessException e) {
        log.error("businessException: " + e.getMessage(), e);
        return ResultUtils.error(e.getCode(), e.getMessage(), e.getDescription());
    }

    @ExceptionHandler(RuntimeException.class)
    public BaseResponse runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, e.getMessage(), "");
    }
}
