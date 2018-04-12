package org.template.cloud.service.service.mysql;


import org.template.cloud.service.domain.bean.mysql.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(String id);

    User findByName(String name);

    int insert(User user);

    int insert(List<User> users);

    int update(User user);

    int delete(String id);

    boolean login(User user, HttpSession session);
}
