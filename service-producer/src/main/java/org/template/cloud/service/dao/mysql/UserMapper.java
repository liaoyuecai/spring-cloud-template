package org.template.cloud.service.dao.mysql;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.template.cloud.service.domain.bean.mysql.User;

import java.util.List;


@Mapper
public interface UserMapper extends ApplicationMapper<User> {
    List<User> findAll();

    User getUserById(String id);

    User getUserByUserName(String userName);

    int saveUser(User user);

    int saveUserList(@Param(value = "users") List<User> users);

    int updateUserByUserName(User user);
}
