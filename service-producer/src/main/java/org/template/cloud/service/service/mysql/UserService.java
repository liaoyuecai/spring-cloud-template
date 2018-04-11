package org.template.cloud.service.service.mysql;


import org.template.cloud.service.domain.bean.mysql.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(String id);

    int insert(User user);

    int update(User user);

    int delete(String id);

    boolean login(User user, HttpSession session);
}
