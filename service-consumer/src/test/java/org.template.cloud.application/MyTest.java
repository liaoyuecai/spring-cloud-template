package org.template.cloud.application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.template.cloud.application.transaction.Transaction;
import org.template.cloud.application.transaction.TransactionBuilder;
import org.template.cloud.application.transaction.service.TransactionService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MyTest {

    @Autowired
    TransactionService transactionService;

    @Test
    public void addTransaction() {
        Transaction transaction = new TransactionBuilder("test").
                addOperation("service", 1, new Object()).
                addOperation("delete", "service", 2, new Object()).get();
        transactionService.createTransaction(transaction);
    }
}
