package com.weirdo.usercenter.common;

/**
 * 错误码
 *
 * @author weirdo
 */

// 有任何错误，我们都直接返回 -1，其实它的意义不是特别好，前端不知道 -1 是什么，后端可能也不知道 -1 代表什么
//所以说我们要定义一个通用的错误码，我们要定义一套错误码的规范。
public enum ErrorCode {

    PARAMS_ERROR(40000, "请求参数错误", ""),
    NULL_ERROR(40001, "请求数据为空", ""),
    NOT_LOGIN(40100, "未登录", ""),
    NO_AUTH(40101, "无权限", ""),
    NOT_FIND_USER(40102,"用户不存在",""),
    OPERATION_ERROR(40200,"操作失败",""),
    SUCCESS(0, "ok", ""),
    SYSTEM_ERROR(50000, "系统内部异常", "");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 状态码信息
     */
    private final String message;

    /**
     * 状态码描述
     */
    private final String description;

    ErrorCode(int code, String message, String description) {
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public String getDescription() {
        return description;
    }
}
