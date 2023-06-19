//  这段代码是一个Java类，表示用户（User）实体对象，用于映射数据库表的结构和字段。
package com.weirdo.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户
 * @TableName user
 */
@TableName(value ="user")  // 通过该注解指定实体类对应的数据库表名为 "user"。
@Data  // 是一个Lombok注解，用于自动生成getter、setter和toString等方法。
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.AUTO)  // 表示该字段是表的主键，并且采用自增长方式生成。
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 账号
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 性别
     */
    private Integer gender;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 电话号码
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0-正常
     */
    private Integer userStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 
     */
    @TableLogic  // 表示该字段是逻辑删除字段，用于标识数据是否被逻辑删除。
    private Integer isDelete;

    /**
     * 用户角色 0 - 普通用户 1 - 管理员
     */
    private Integer userRole;

    /**
     * 星球编号
     */
    private String planetCode;
    @TableField(exist = false)  // 该注解表示该字段在数据库表中不存在，用于标识该字段是一个非数据库字段。
    private static final long serialVersionUID = 1L;
}