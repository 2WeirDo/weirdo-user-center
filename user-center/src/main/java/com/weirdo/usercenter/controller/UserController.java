//就是请求层或者叫控制层。然后这个目录里的所有的文件专门用来接收请求，不做任何的业务处理，只去接收请求
// 这段代码是一个用户接口控制器（UserController），用于处理与用户相关的请求和业务逻辑。

package com.weirdo.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.weirdo.usercenter.common.BaseResponse;
import com.weirdo.usercenter.common.ErrorCode;
import com.weirdo.usercenter.common.ResultUtils;
import com.weirdo.usercenter.exception.BusinessException;
import com.weirdo.usercenter.model.domain.User;
import com.weirdo.usercenter.model.domain.request.*;
import com.weirdo.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.weirdo.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.weirdo.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
 * 用户接口
 *
 * @author weirdo
 */

@RestController  // 适用于编写 restful 风格的 api，返回值默认为 json 类型
@RequestMapping("/user")  // 表示该控制器处理的请求路径为 "/user"，即该控制器下的接口路径都以 "/user" 开头。
public class UserController {

    @Resource  // 是Java EE提供的注解，用于标记资源的注入，这里用于注入 UserService。
    private UserService userService;
    @PostMapping("/register")  // 表示处理POST请求的 "/register" 接口，用于用户注册。
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        // 处理用户注册请求的方法，接收一个 UserRegisterRequest 对象作为请求体，并返回一个包含响应数据的 BaseResponse 对象。
        // 打上一个注解@RequestBody，没打上的话，Springmvc框架不知道怎么把前端传来的json参数去合适的对象做一个关联
        if(userRegisterRequest == null) {
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if(StringUtils.isAllBlank(userAccount, userPassword, checkPassword, planetCode)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(result);
    }
// 该方法的作用是接收用户注册请求，提取请求中的用户信息，调用userService进行用户注册操作，并将注册结果封装到BaseResponse对象中返回给调用方。


    @PostMapping("/login")  // 表示处理POST请求的 "/login" 接口，用于用户登录。
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        // 处理用户登录请求的方法，接收一个 UserLoginRequest 对象作为请求体和 HttpServletRequest 对象，
        // 用于获取HTTP请求的信息，并返回一个包含响应数据的 BaseResponse 对象。
        // 打上一个注解@RequestBody，没打上的话，Springmvc框架不知道怎么把前端传来的json参数去合适的对象做一个关联
        if(userLoginRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAllBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "不能存在空白");
        }
        User user = userService.doLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }
    // 该方法的作用是接收用户登录请求，提取请求中的用户账号和密码，调用userService进行用户登录操作，
    // 并将登录结果封装到BaseResponse对象中返回给调用方。
    // 同时，通过HttpServletRequest对象可以获取HTTP请求的相关信息，用于处理登录逻辑。

    @GetMapping("/current")  // 表示处理GET请求的 "/current" 接口，用于获取当前登录用户的信息。
    public BaseResponse<User> getCurrentUser(HttpServletRequest request){
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;  // 将获取到的用户信息转换为User对象。
        if (currentUser == null){
            throw new BusinessException(ErrorCode.NOT_LOGIN);
        }
        long userId = currentUser.getId();
        // todo 校验用户是否合法
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser); // 将安全处理后的用户信息封装为一个成功的响应对象，并返回给调用方。
    }

    /*该方法的目的是获取当前登录用户的信息并进行返回，如果用户未登录，则会抛出异常表示未登录的错误情况。*/


    @GetMapping("/search")  // 表示处理GET请求的 "/search" 接口，用于搜索用户列表。
    public BaseResponse<List<User>> searchUsers(String useraccount, HttpServletRequest request){
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();  // 创建一个QueryWrapper对象，用于构建查询条件。
        if (StringUtils.isNoneBlank(useraccount)) {
            queryWrapper.like("useraccount", useraccount);
        }
        List<User> userList = userService.list(queryWrapper);
        // 将安全处理后的用户列表封装为一个成功的响应对象，并返回给调用方。
        List<User> list = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(list);
    }
    // 该方法的目的是根据给定的查询条件搜索用户列表，并进行安全处理后返回结果。如果当前用户不是管理员，则会抛出异常表示参数错误的情况。

    @PostMapping("/delete") // 表示处理POST请求的 "/delete" 接口，用于删除用户。
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request){
        // 处理删除用户请求的方法，接收一个用户id作为请求体和 HttpServletRequest 对象，
        // 用于获取HTTP请求的信息，并返回一个包含响应数据的 BaseResponse 对象。
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH, "无权限");
        }
        if(id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        // 根据传入的用户id执行删除操作，返回删除结果。
        // removeById() 是 userServive 继承的方法
        return ResultUtils.success(b);
    }

    @PostMapping("/delete2")
    public BaseResponse<Boolean> deleteUser(@RequestBody UserDeleteRequest deleteRequest, HttpServletRequest request){
        //是否为管理员
        if(!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH,"无权限");
        }

        if(deleteRequest == null || deleteRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        boolean removeUser = userService.removeById(deleteRequest.getId());

        return ResultUtils.success(removeUser);
    }

    /**
     * 创建用户
     *
     * @param createUserRequest
     * @param request
     * @return
     */
    @PostMapping("/add")
    public BaseResponse<Long> addUser(@RequestBody CreateUserRequest createUserRequest,HttpServletRequest request){
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无权限");
        }
        if (createUserRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求参数为空");
        }
        User user = new User();
        BeanUtils.copyProperties(createUserRequest, user);
        System.out.println("新增用户===="+user);
        boolean result = userService.save(user);
        //ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        if (!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR);
        }
        return ResultUtils.success(user.getId());
    }


    @GetMapping("/search2")
    public BaseResponse<List<User>> searchUsers(UserSearchRequest searchRequest, HttpServletRequest request) {
        // 管理员校验
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH, "无权限");
        }
        String username = searchRequest.getUsername();
        String userAccount = searchRequest.getUserAccount();
        Integer gender = searchRequest.getGender();
        String phone = searchRequest.getPhone();
        String email = searchRequest.getEmail();
        Integer userStatus = searchRequest.getUserStatus();
        Integer userRole = searchRequest.getUserRole();
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        //Date updateTime = searchRequest.getUpdateTime();
        Date createTime = searchRequest.getCreateTime();
        // username
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        // userAccount
        if (StringUtils.isNotBlank(userAccount)) {
            queryWrapper.like("userAccount", userAccount);
        }
        // gender
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.eq("gender", gender);
        }
        // phone
        if (StringUtils.isNotBlank(phone)) {
            queryWrapper.like("phone", phone);
        }
        // email
        if (StringUtils.isNotBlank(email)) {
            queryWrapper.like("email", email);
        }
        // userStatus
        if (userStatus != null) {
            queryWrapper.eq("userStatus", userStatus);
        }

        if (userRole != null) {
            queryWrapper.eq("userRole", userRole);
        }

//        if (updateTime != null) {
//            queryWrapper.like("updateTime", updateTime);
//        }
        if (createTime != null) {
            queryWrapper.like("createTime", createTime);
        }
        List<User> userList = userService.list(queryWrapper);
        List<User> users = userList.stream().map(userService::getSafetyUser).collect(Collectors.toList());
        return ResultUtils.success(users);
    }

    /**
     * 修改密码
     *
     * @param updatePasswordRequest
     * @param request
     * @return
     */
    @PostMapping("/update/password")
    public BaseResponse<Boolean> updateUserPassword(@RequestBody UserUpdatePasswordRequest updatePasswordRequest,
                                                    HttpServletRequest request) {

        boolean updateUserPassword = userService.userUpdatePassword(updatePasswordRequest, request);
        if (updateUserPassword) {
            return ResultUtils.success(true);
        } else {
            return ResultUtils.error(ErrorCode.OPERATION_ERROR,"修改密码失败");
        }
    }

    @PostMapping("/update/myUser")
    public BaseResponse<Boolean> updateMyUser(@RequestBody UserUpdateRequest userUpdateRequest){

        boolean updateMyUser = userService.updateMyUser(userUpdateRequest);

        if (updateMyUser) {
            return ResultUtils.success(true);
        } else {
            return ResultUtils.error(ErrorCode.OPERATION_ERROR,"修改信息失败");
        }
    }

    @PostMapping("/update/admin")
    public BaseResponse<Boolean> adminUpdateUser(@RequestBody AdminUpdateRequest adminUpdateRequest, HttpServletRequest request){

        if (!isAdmin(request)){
            throw new BusinessException(ErrorCode.NO_AUTH, "无权限");
        }

        boolean adminUpdateUser = userService.adminUpdateUser(adminUpdateRequest);

        if (adminUpdateUser) {
            return ResultUtils.success(true);
        } else {
            return ResultUtils.error(ErrorCode.OPERATION_ERROR,"修改信息失败");
        }
    }

    @PostMapping("/logout")  // 表示处理POST请求的 "/logout" 接口，用于用户注销。
    public BaseResponse<Integer> userLogout(HttpServletRequest request){
        if(request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int result = userService.userLogout(request);
        // UserServicelmpl 中写的 userLogout 从会话中移除名为 USER_LOGIN_STATE 的属性，即移除用户的登录态。
        return ResultUtils.success(result);
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request){
        // 用于判断当前登录用户是否为管理员，接收一个 HttpServletRequest 对象用于获取HTTP请求的信息，并返回一个布尔值表示是否为管理员。
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;  //  将 userObj 强制转换为 User 对象，表示当前登录用户。
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

}
