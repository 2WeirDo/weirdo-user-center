package com.weirdo.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserDeleteRequest implements Serializable {

    private static final long serialVersionUID = 3557511145491756587L;

    private long id;

}