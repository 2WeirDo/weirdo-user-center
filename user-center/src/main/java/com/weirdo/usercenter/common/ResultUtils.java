package com.weirdo.usercenter.common;

/**
 * 返回工具类
 *
 * @author weirdo
 */
public class ResultUtils {
    /*这些方法提供了一种简便的方式来创建成功或失败的响应对象，以及在错误情况下提供不同的错误码、错误消息和错误描述。
    它们可以在控制器中使用，将方法的返回值包装为合适地响应对象，并返回给调用方。*/

    /**
     * 成功
     * @param data
     * @return
     * @param <T>
     */
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode){
        return new BaseResponse<>(errorCode);
    }

    public static BaseResponse error(int code, String message, String description){
        return new BaseResponse(code,  null,  message, description);
    }
    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String message, String description){
        return new BaseResponse(errorCode.getCode(), null,  message, description);
    }

    /**
     * 失败
     * @param errorCode
     * @return
     */
    public static BaseResponse error(ErrorCode errorCode, String description){
        return new BaseResponse(errorCode.getCode(), errorCode.getMessage() ,description);
    }
}
