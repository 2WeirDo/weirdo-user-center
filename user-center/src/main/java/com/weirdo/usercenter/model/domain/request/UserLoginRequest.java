package com.weirdo.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
// 封装一个对象，专门用来接收请求参数
/**
 * 用户登录请求体
 *
 * @author weirdo
 */
@Data   // Lombok注解，用于自动生成getter、setter和其他通用方法。
public class UserLoginRequest implements Serializable {
    // 表示用户登录请求的数据模型。它实现了Serializable接口，以便该对象可以在网络中进行序列化和反序列化传输。

    @Serial
    private static final long serialVersionUID = -2040803633561243508L;
    private String userAccount;
    private String userPassword;

// 在用户登录接口中，通过接收UserLoginRequest对象作为请求体，可以获取用户提交的登录信息。
    // 在 UserController 中便是这样
}
