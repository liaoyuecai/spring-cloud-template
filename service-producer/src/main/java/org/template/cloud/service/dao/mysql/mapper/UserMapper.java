package org.template.cloud.service.dao.mysql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.template.cloud.bean.module.User;
import org.template.cloud.service.dao.mysql.ServiceMapper;

import java.util.List;


@Mapper
public interface UserMapper extends ServiceMapper<User> {
    List<User> findAll();

    User getUserById(String id);

    User getUserByUserName(String userName);

    int saveUser(User user);

    int saveUserList(@Param(value = "users") List<User> users);

    int updateUserByUserName(User user);
}
