package com.weirdo.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserUpdatePasswordRequest implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 旧密码
     */
    private String userPassword;

    /**
     * 新密码
     * 要和前端对应newPassword
     */
    private String newPassword;

}