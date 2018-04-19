package org.template.cloud.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.template.cloud.bean.constant.ServerApiProperties;
import org.template.cloud.bean.transaction.Transaction;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MyTest {

    @Test
    public void addTransaction() {
        User user = new User();
        user.setPassword("122333");
        user.setUserName("lixiaolong");
        Transaction transaction = new Transaction().
                addOperation("serviceQueue", ServerApiProperties.USER_INSERT, user);
        transaction.setName("test");
        user.setPassword("askhdalk");
        transaction.addOperation("userUpdate", "serviceQueue", ServerApiProperties.USER_UPDATE, user);
//        transactionService.createTransaction(transaction);
    }
}
