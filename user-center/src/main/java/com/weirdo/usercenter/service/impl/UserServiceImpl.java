//专门用来编写业务逻辑,那比如说登录注册这些就是业务逻辑
// userservice实现类
//  这段代码是`UserService`接口的实现类`UserServiceImpl`，用于实现具体的业务逻辑。

package com.weirdo.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weirdo.usercenter.common.ErrorCode;
import com.weirdo.usercenter.exception.BusinessException;
import com.weirdo.usercenter.model.domain.User;
import com.weirdo.usercenter.model.domain.request.AdminUpdateRequest;
import com.weirdo.usercenter.model.domain.request.UserUpdatePasswordRequest;
import com.weirdo.usercenter.model.domain.request.UserUpdateRequest;
import com.weirdo.usercenter.service.UserService;
import com.weirdo.usercenter.Mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.weirdo.usercenter.constant.UserConstant.*;

/**
  * @author weirdo
  * 用户服务实现类
  *
  */

// 该类通过`@Service`注解标记为Spring的服务组件，表示它是一个可被依赖注入的服务类。
// 该类还继承了`ServiceImpl<UserMapper, User>`类，
// 该类是MyBatis-Plus框架提供的基础服务类，提供了一些常用的数据库操作方法的实现，如增删改查等。
// 通过编写这些方法的具体实现，该类承担了用户相关业务逻辑的处理，供其他模块调用和使用。


@Service
@Slf4j
// 打上slf4j这个注解之后,就可以在当前的这个类中使用log，用log来记录日志，这样后面系统出了问题，可以在日志中去查找，类似于监控
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Resource
    private UserMapper userMapper;

    /**
     * 盐值, 混淆密码
     */
    private static final  String SALT = "weirdo";



    /*实现了`userRegister`方法，用于用户注册功能。
    在注册之前进行了一系列的参数校验，包括检查参数是否为空、账号和密码的长度、账号是否包含特殊字符等。
     * 然后对密码进行加密处理，并将用户信息插入数据库表中。*/
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        // 1. 校验
        if (StringUtils.isAllBlank(userAccount, userPassword, checkPassword, planetCode)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if (planetCode.length() > 5){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "星球编号过长");
        }
        // 账户不能包含特殊字符
        String validPattern =  "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        // 如果包含非法字符，则返回
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号包含非法字符");
        }
        // 1.5. 密码和校验密码不相同
        if(!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请确认密码一致");
        }

        // 账户不能重复
        /*QueryWrapper 是 MyBatis-Plus 框架提供的一个查询构造器，它用于构建数据库查询条件。通过创建 QueryWrapper 对象，
        可以使用链式方法来设置查询条件，如 eq、like、in 等，以及排序、分页等操作。*/
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 设置查询条件, userAccount == userAccount
        queryWrapper.eq("userAccount", userAccount);
        // 统计满足条件的记录数量。
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }

        // 星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "编号重复");
        }

        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 3. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        user.setUsername(DEFAULT_USERNAME);
        user.setPhone(DEFAULT_PHONE);
        user.setGender(DEFAULT_GENDER);
        user.setEmail(DEFAULT_EMAIL);
        user.setAvatarUrl(DEFAULT_AVATAR_URL);
        boolean saveResult = this.save(user);
        /*通过调用save方法将用户数据插入数据库表中，save方法返回一个布尔值，表示插入操作是否成功。
        如果插入成功，save方法返回true，否则返回false。*/
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

//    @Override
//    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
//        return null;
//    }


    /*实现了`doLogin`方法，用于用户登录功能。同样进行了参数校验，然后对密码进行加密处理，通过查询数据库验证用户账号和密码是否匹配，
     * 如果匹配则返回脱敏后的用户信息，并记录用户的登录状态。*/
    @Override  // 用于标记方法覆盖（重写）父类或接口中的方法。它是一种编译时检查的机制，可以确保方法的正确重写。
    public User doLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1. 校验
        if (StringUtils.isAllBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }

        // 账户不能包含特殊字符
        String validPattern =  "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        // 如果包含非法字符，则返回
        if(matcher.find()){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号包含非法字符");
        }


        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在 , 构建查询条件
        /*通过调用 eq 方法设置两个查询条件：userAccount 等于输入的用户账号，userPassword 等于加密后的密码 encryptPassword。*/
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        /*查询满足条件的用户信息。如果查询结果为 null，表示用户不存在或账号密码不匹配，
        如果查询结果不为 null，则表示用户存在且账号密码匹配成功。*/
        User user = userMapper.selectOne(queryWrapper);

        // 用户不存在
        if(user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号或密码错误");
        }
        // 3. 用户脱敏
        User safetyUser = getSafetyUser(user);

        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        /*将脱敏后的用户对象 safetyUser 存储到当前会话的属性中，属性名为 USER_LOGIN_STATE。
         这样做的目的是记录用户的登录态，使得其他操作可以通过访问会话属性来获取当前登录的用户信息。*/
        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param originUser
     * @return
     */

    /*实现了`getSafetyUser`方法，用于对用户信息进行脱敏处理。创建一个新的`User`对象，并将原始用户对象中的部分敏感信息复制到新对象中返回。*/
    @Override  // 用于标记方法覆盖（重写）父类或接口中的方法。它是一种编译时检查的机制，可以确保方法的正确重写。
    public User getSafetyUser(User originUser){
        if (originUser == null){
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }

    /**
     * 用户注销
     * @param request
     */

    /// 通过移除会话中的特定属性来注销用户，即移除用户的登录态，并返回一个表示注销状态的整数值。
    @Override  // 用于标记方法覆盖（重写）父类或接口中的方法。它是一种编译时检查的机制，可以确保方法的正确重写。
    public int userLogout(HttpServletRequest request) {
        // 接收一个 HttpServletRequest 对象作为参数，用于获取当前用户的会话信息。
        // 移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        // 从会话中移除名为 USER_LOGIN_STATE 的属性，即移除用户的登录态。
        return 1;
        // 返回整数值 1，表示注销成功。
    }
    /**
     * 修改密码
     *
     * @param userUpdatePasswordRequest
     * @param request
     * @return
     */
    public boolean userUpdatePassword(UserUpdatePasswordRequest userUpdatePasswordRequest, HttpServletRequest request) {
        if (userUpdatePasswordRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"请求为空");
        }

        //获取当前用户
        User loginUser = getLoginUser(request);
        Long currentUserId = loginUser.getId();
        if (currentUserId < 0 || currentUserId == null){
            throw new BusinessException(ErrorCode.NOT_FIND_USER,"用户不存在");
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdatePasswordRequest,user);
        user.setId(loginUser.getId());
        //加密新密码
        String encryptedPassword = DigestUtils.md5DigestAsHex((SALT + userUpdatePasswordRequest.getNewPassword()).getBytes());

        user.setUserPassword(encryptedPassword);
        //两次输入的密码一样
        if (encryptedPassword.equals(userUpdatePasswordRequest.getUserPassword())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "修改密码不能相同");
        }


        //更新数据
        boolean result = updateById(user);
        if (!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"更新失败");
        }
        return true;
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "未登录");
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN, "未登录");
        }
        return currentUser;
    }

    /**
     * 用户更新自己的信息
     *
     * @param userUpdateRequest
     *
     * @return
     */
    public boolean updateMyUser(UserUpdateRequest userUpdateRequest) {

        if (userUpdateRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求为空");
        }

        //获取当前用户
        Long currentUserId = userUpdateRequest.getId();
        if (currentUserId < 0 || currentUserId == null){
            throw new BusinessException(ErrorCode.NOT_FIND_USER,"用户不存在");
        }
        User user = new User();
        BeanUtils.copyProperties(userUpdateRequest,user);
        user.setId(currentUserId);

        user.setUsername(userUpdateRequest.getUsername());
        user.setEmail(userUpdateRequest.getEmail());
        user.setGender(userUpdateRequest.getGender());
        user.setPhone(userUpdateRequest.getPhone());
        user.setAvatarUrl(userUpdateRequest.getAvatarUrl());
        //更新数据
        boolean result = updateById(user);
        if (!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"更新失败");
        }
        return true;
    }

    /**
     * 管理员修改用户信息
     *
     * @param adminUpdateRequest
     * @return
     */
    public boolean adminUpdateUser(AdminUpdateRequest adminUpdateRequest) {

        if (adminUpdateRequest == null){
            throw new BusinessException(ErrorCode.NULL_ERROR,"请求为空");
        }

        //获取当前用户
        Long currentUserId = adminUpdateRequest.getId();
        if (currentUserId < 0 || currentUserId == null){
            throw new BusinessException(ErrorCode.NOT_FIND_USER,"用户不存在");
        }
        User user = new User();
        BeanUtils.copyProperties(adminUpdateRequest,user);
        user.setId(currentUserId);

        user.setUsername(adminUpdateRequest.getUsername());
        user.setEmail(adminUpdateRequest.getEmail());
        user.setGender(adminUpdateRequest.getGender());
        user.setPhone(adminUpdateRequest.getPhone());
        user.setAvatarUrl(adminUpdateRequest.getAvatarUrl());
        user.setUserRole(adminUpdateRequest.getUserRole());
        user.setUserAccount(adminUpdateRequest.getUserAccount());
        user.setUserStatus(adminUpdateRequest.getUserStatus());

        //更新数据
        boolean result = updateById(user);
        if (!result){
            throw new BusinessException(ErrorCode.OPERATION_ERROR,"更新失败");
        }
        return true;
    }

}




