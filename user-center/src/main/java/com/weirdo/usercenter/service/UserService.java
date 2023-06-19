package com.weirdo.usercenter.service;

import com.weirdo.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weirdo.usercenter.model.domain.request.AdminUpdateRequest;
import com.weirdo.usercenter.model.domain.request.UserUpdatePasswordRequest;
import com.weirdo.usercenter.model.domain.request.UserUpdateRequest;
import org.springframework.http.HttpRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * @author 16064
 * @description 针对表【user(用户)】的数据库操作Service
 * @createDate 2023-06-13 09:07:30
 */
public interface UserService extends IService<User> {

// 这段代码定义了一个UserService接口，它是一个针对表user(用户)的数据库操作服务接口。
// 该接口继承了IService<User>接口，后者是MyBatis-Plus框架提供的基础服务接口。
//接口中定义了一系列方法来处理用户相关的操作，包括用户注册、用户登录、用户脱敏和用户注销等。这些方法提供了对用户表进行增删改查的功能。


//    写完接收参数再写注释
//    这种注释类型是 java doc 的注释类型
    /**
     * 用户注册
     * @param userAccount   用户账户
     * @param userPassword  用户密码
     * @param checkPassword 校验密码
     * @param planetCode 星球编号
     * @return 新用户 id
     */
    long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode);



    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */

//    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    User doLogin(String userAccount, String userPassword, HttpServletRequest request);
/*request 参数是用于获取客户端的请求信息，
例如获取客户端的 IP 地址、请求头信息等。在登录过程中，可能需要获取客户端的一些信息进行验证或记录日志等操作。*/
    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);
/*因为该方法只是对用户信息进行处理，与客户端请求无关。 不用 request 参数*/
    /**
     * 用户注销
     *
     * @param request
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     *修改密码
     *
     * @param userUpdatePasswordRequest
     * @param request
     * @return
     */
    boolean userUpdatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest,HttpServletRequest request);

    /**
     * 获取当前用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户修改自己的信息
     *
     * @param userUpdateRequest
     *
     * @return
     */
    boolean updateMyUser(UserUpdateRequest userUpdateRequest);

    /**
     * 管理员修改用户的信息
     *
     * @param adminUpdateRequest
     * @return
     */
    boolean adminUpdateUser(AdminUpdateRequest adminUpdateRequest);
}
// 该接口提供了对用户表进行操作的服务，其他模块可以通过调用这些方法来实现对用户数据的增删改查以及相关业务逻辑的处理。