package com.weirdo.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户注册请求体
 *
 * @author weirdo
 */
@Data
public class UserRegisterRequest implements Serializable {

    @Serial
    private static final long serialVersionUID = -2040803633561243508L;
    private String userAccount;
    private String userPassword;
    private String checkPassword;
    private String planetCode;

}
