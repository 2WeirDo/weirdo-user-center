package com.weirdo.usercenter.common;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 通用返回类
 *
 * @param <T>
 * @author weirdo
 */
@Data
public class BaseResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1720667998886934179L;
    /*
    *
    这段代码定义了一个通用的返回类 BaseResponse，用于封装接口的响应数据。它包含以下属性：
    code：表示响应的状态码，用整数值表示不同的状态。
    data：表示响应的数据内容，可以是任意类型的对象，用泛型 T 表示。
    message：表示响应的消息或描述信息，用字符串表示。
    description：表示响应的详细描述信息，用字符串表示。
    
    BaseResponse 的作用是统一规范接口的响应格式，将响应的状态码、数据、消息和描述信息封装到一个对象中，方便接口的调用方获取和解析响应数据。
    
    BaseResponse 还提供了不同的构造方法，以便根据不同的情况创建对象，
    * 包括传入状态码、数据、消息和描述信息，或者直接使用预定义的 ErrorCode 枚举类型来创建对象。
    。*/
    private int code;
    private T data;  // * 通过使用泛型 T，BaseResponse 可以适应不同类型的数据，使得接口的返回数据更加灵活。
    private String message;

    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }
    public BaseResponse(int code, T data, String message) {
        this(code, data, message, "");
    }

    public BaseResponse(int code, T data) {
        this(code, data, "", "");
    }

    public BaseResponse(ErrorCode errorCode){
        this(errorCode.getCode(), null, errorCode.getMessage(), errorCode.getDescription());
    }
}

  //  使用 BaseResponse 可以使接口的响应格式更加统一和规范化，便于前端或其他调用方对响应数据进行处理和解析
