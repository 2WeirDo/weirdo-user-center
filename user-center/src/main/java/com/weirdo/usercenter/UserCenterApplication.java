package com.weirdo.usercenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// 在Spring Boot中，通过添加@MapperScan注解到启动类上，可以告诉Spring框架扫描指定的包路径，
// 并自动将其中的Mapper接口注册为Spring容器中的Bean。
@MapperScan("com.weirdo.usercenter.Mapper")
// 通过@MapperScan注解，Spring Boot能够自动扫描指定的Mapper接口所在的包路径，并为这些接口生成代理对象，并将其注册为Spring容器中的Bean。
// 这样，我们就可以在其他地方（例如Service层）直接注入这些Mapper接口，并使用它们的方法进行数据库操作，而不需要手动编写实现类。
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

}
