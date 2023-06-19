package com.weirdo.usercenter.constant;

/**
 * 用户常量
 *
 * @author weirdo
 */
public interface UserConstant {
/*通过定义这些常量，可以在代码中统一引用，提高代码的可读性和可维护性。
* 在不同的类或方法中需要使用用户登录态的键时，可以直接引用 UserConstant.USER_LOGIN_STATE 而不是直接写字符串，避免出错和重复代码。
* 同样，可以使用 UserConstant.DEFAULT_ROLE 和 UserConstant.ADMIN_ROLE 来表示不同的用户权限级别，增加代码的可读性和可维护性。*/
    /**
     * 用户登录态键
     */
    String USER_LOGIN_STATE = "userLoginState";
    /*用于在会话（session）中存储用户的登录状态。在登录成功后，将用户对象保存在会话中，
    通过该键可以获取用户的登录状态，以便在需要时验证用户的身份或获取用户信息。*/

    // ------- 权限 -------
    /**
     * 默认权限
     */
    int DEFAULT_ROLE = 0;
    /*表示用户的默认权限级别。*/
    /**
     * 管理员权限
     */
    int ADMIN_ROLE = 1;
    /*，表示用户的管理员权限级别。*/


    /**
     * 默认头像
     */
    String DEFAULT_AVATAR_URL = "https://image.meiye.art/pic_TyXVqdRWmv9iHCkj7oMoh?imageMogr2/thumbnail/620x/interlace/1";

    /**
     * 默认性别
     */
    int DEFAULT_GENDER = 0;

    /**
     * 默认邮箱
     */
    String DEFAULT_EMAIL = "123456@qq.com";

    /**
     * 默认手机号
     */
    String DEFAULT_PHONE = "13812345678";

    /**
     * 默认用户名
     */
    String DEFAULT_USERNAME = "user_";

}
