package org.template.cloud.service.service.mysql;


import org.template.cloud.bean.module.User;
import org.template.cloud.bean.transaction.Transaction;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(String id);

    User findByName(String name);

    int insert(User user);

    boolean insert(Transaction transaction);

    int insert(List<User> users);

    int update(User user);

    boolean update(Transaction transaction);

    int delete(String id);

    boolean login(User user, HttpSession session);
}
