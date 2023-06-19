// 一个数据访问层，就是这一层的文件，就是专门用于从数据库中去查询数据，去取数据、增删改查之类的
package com.weirdo.usercenter.Mapper;  // 表示该Mapper接口所在的包路径。

import com.weirdo.usercenter.model.domain.User;  // 导入了User类，用于定义数据库表"user"对应的Java对象。
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
// 表示UserMapper接口继承了BaseMapper接口，并指定了泛型参数为User。
// BaseMapper是MyBatis-Plus框架提供的基础Mapper接口，封装了常见的数据库操作方法，如插入、更新、删除、查询等。
/**
* @author 16064
* @description 针对表【user(用户)】的数据库操作Mapper
* @createDate 2023-06-13 09:07:30
* @Entity generator.domain.User
*/
public interface UserMapper extends BaseMapper<User> {

}

// 该Mapper接口的作用是定义了针对数据库表"user"的数据库操作方法。通过继承BaseMapper接口，
// UserMapper接口可以直接使用BaseMapper提供的通用数据库操作方法，无需手动编写SQL语句，从而简化了数据库访问层的开发工作。


