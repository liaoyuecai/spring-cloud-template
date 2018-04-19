package org.template.cloud.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.template.cloud.transaction.Transaction;
import org.template.cloud.config.ServiceApiConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MyTest {

    @Test
    public void addTransaction() {
        User user = new User();
        user.setPassword("122333");
        user.setUserName("lixiaolong");
        Transaction transaction = new Transaction().
                addOperation("serviceQueue", ServiceApiConfig.USER_INSERT, user);
        transaction.setName("test");
        user.setPassword("askhdalk");
        transaction.addOperation("userUpdate", "serviceQueue", ServiceApiConfig.USER_UPDATE, user);
//        transactionService.createTransaction(transaction);
    }
}
