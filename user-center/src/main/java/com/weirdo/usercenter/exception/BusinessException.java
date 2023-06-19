package com.weirdo.usercenter.exception;

import com.weirdo.usercenter.common.ErrorCode;

import java.io.Serial;

/**
 * 自定义异常类
 *
 * @author weirdo
 */

// 这段代码是一个自定义的业务异常类 BusinessException，用于在业务逻辑处理过程中抛出异常并携带错误码和错误描述信息。
 // 可以捕获到抛出的业务异常和运行时异常，并将其转换为统一的响应格式，以便统一处理和返回给客户端。
public class  BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 7730273167407775024L;
    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
