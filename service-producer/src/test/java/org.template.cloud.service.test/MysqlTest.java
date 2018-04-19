package org.template.cloud.service.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.template.cloud.service.ServiceApplication;
import org.template.cloud.service.domain.bean.mysql.User;
import org.template.cloud.service.service.mysql.UserService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceApplication.class)
public class MysqlTest {
    @Autowired
    UserService userService;
    @Autowired

    public void userTest() {
        User user = new User();
        user.setUserName("admin");
        user.setPassword("123456");
        userService.insert(user);
        List<User> list = userService.findAll();
        System.out.println(list.size());
    }

    @Test
    public void insertList() {
        List<User> list = new ArrayList();
        User user;
        for (int i = 0; i < 50; i++) {
            user = new User();
            user.setUserName("No" + i);
            user.setPassword("123456");
            list.add(user);
        }
        userService.insert(list);
    }

    @Test
    public void findUsers() {
        List<User> list = userService.findAll();
        for (User user : list) {
            System.out.println(user.getUserName());
        }
    }

}
