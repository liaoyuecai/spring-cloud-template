package org.template.cloud.service.service.mysql.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.template.cloud.service.dao.mysql.UserMapper;
import org.template.cloud.service.domain.bean.mysql.User;
import org.template.cloud.service.service.mysql.UserService;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    @Override
    public User findById(String id) {
        return null;
    }

    @Override
    public int insert(User user) {
        return 0;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public int delete(String id) {
        return 0;
    }

    @Override
    public boolean login(User user, HttpSession session) {
        User src = userMapper.getUserByUserName(user.getUserName());
        if (src != null && src.getPassword().equals(user.getPassword())) {
            session.setAttribute("user", src);
            return true;
        } else {
            return false;
        }
    }

}
