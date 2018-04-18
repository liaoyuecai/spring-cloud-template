package org.template.cloud.service.transaction;


import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.template.cloud.service.transaction.bean.TransactionFail;
import org.template.cloud.service.transaction.service.TransactionService;

@Component
@RabbitListener(queues = "serviceQueue")
@Log4j
class TransactionTask {

    String serviceQueueName = "serviceQueue";

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    TransactionService transactionService;
    @Autowired
    ServiceApiExecutor serviceApiExecutor;

    @RabbitHandler
    public void receive(String message) {
        try {
            transaction(message);
        } catch (Exception e) {
            log.error("事务处理异常", e);
        }
    }

    void transaction(String message) {
        Transaction transaction = null;
        int status = 0;
        boolean validity = true;
        boolean executeStatus = true;
        try {
            transaction = JSON.parseObject(message, Transaction.class);
            status = transaction.getStatus();
            int result = 0;
            while (transaction.hasNext() && executeStatus && validity &&
                    serviceQueueName.equals(transaction.topic())) {
                if (transaction.hasNext())
                    result = serviceApiExecutor.execute(transaction.next());
                executeStatus = result == ServiceApiExecutor.SUCCESS;
                validity = result != ServiceApiExecutor.EXECUTED && result != ServiceApiExecutor.USELESS;
                if (executeStatus)
                    transaction.complete();
            }
        } catch (Exception e) {
            fail(transaction, e);
            executeStatus = false;
            throw new RuntimeException(e);
        } finally {
            if (transaction != null && validity) {
                boolean statusChange = transaction.getStatus() == status;
                next(transaction, statusChange, executeStatus);
            }
        }
    }

    void next(Transaction transaction, boolean statusChange, boolean executeStatus) {
        if (!statusChange)
            transactionService.updateTransaction(transaction);
        if (transaction.hasNext() && executeStatus)
            rabbitTemplate.convertAndSend(transaction.topic(), JSON.toJSONString(transaction));
    }

    void fail(Transaction transaction, Exception e) {
        String operationId = transaction.defeated();
        TransactionFail fail = new TransactionFail();
        fail.setTransactionId(transaction.getId());
        fail.setOperationId(operationId);
        fail.setFailCause(e.getMessage());
        transactionService.operationFail(fail);
    }
}
