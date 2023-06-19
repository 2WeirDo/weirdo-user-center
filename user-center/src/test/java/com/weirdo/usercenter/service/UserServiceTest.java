package com.weirdo.usercenter.service;
import java.util.Date;

import com.weirdo.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

// 用户服务测试  @author weirdo
@SpringBootTest
public class UserServiceTest {

    // @Resource注解是一种方式，用于告诉Java运行时环境，你需要在特定位置注入（提供）一个与之匹配的对象，以供你在代码中使用。
    // 这样，你就可以方便地使用其他类、服务或对象，而无需手动创建它们或传递给相应的地方。
    @Resource
    private UserService userService;
    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("dogyupi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://2weirdo.github.io/img/messi.jpg");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        boolean result = userService.save(user);
//        最后他的返回结果是一个布尔类型的，就表示他是否创建成功
        System.out.println(user.getId());
        assertTrue(result);
    }

    @Test
    void userRegister() {
        String userAccount = "yupi";
        String userPassword = "";
        String checkPassword = "123456";
        String planetCode = "1";
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yupi";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "dogyupi";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, result);
        userAccount = "yupi";
        result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1,result);
    }
}