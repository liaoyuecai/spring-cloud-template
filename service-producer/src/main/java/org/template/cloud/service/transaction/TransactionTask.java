package org.template.cloud.service.transaction;


import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.template.cloud.bean.constant.ConstantProperties;
import org.template.cloud.bean.transaction.Transaction;
import org.template.cloud.bean.transaction.TransactionFail;
import org.template.cloud.service.remote.TransactionRemote;

@Component
@RabbitListener(queues = "serviceQueue")
@Log4j
class TransactionTask {

    String serviceQueueName = "serviceQueue";

    @Autowired
    ServiceApiExecutor serviceApiExecutor;

    @Autowired
    TransactionRemote transactionRemote;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @RabbitHandler
    public void receive(String message) {
        Transaction transaction = null;
        try {
            transaction = JSON.parseObject(message, Transaction.class);
            boolean status = true;
            while (transaction.hasNext() && status &&
                    serviceQueueName.equals(transaction.topic())) {
                status = serviceApiExecutor.execute(transaction);
            }
            if (status)
                transactionLog(transaction);
            //如果事务还有未完成操作且当前操作成功时,将事务提交mq
            if (transaction.hasNext() && status)
                rabbitTemplate.convertAndSend(transaction.topic(), JSON.toJSONString(transaction));
        } catch (Exception e) {
            fail(transaction, e);
            log.error("事务处理异常", e);
        }
    }

    /**
     * 如事务完结或失败则记录事务日志
     *
     * @param transaction
     */
    private void transactionLog(Transaction transaction) {
        if (!transaction.hasNext() || transaction.getStatus() == ConstantProperties.TRANSACTION_FAIL)
            transactionRemote.updateTransaction(JSON.toJSONString(transaction));
    }


    /**
     * 当前操作失败方法
     * 保存事务日志
     * 先保存失败记录再记录其他日志
     *
     * @param transaction
     */
    protected void fail(Transaction transaction, Exception e) {
        String operationId = transaction.defeated();
        TransactionFail fail = new TransactionFail();
        fail.setTransactionId(transaction.getId());
        fail.setOperationId(operationId);
        fail.setFailCause(e.getMessage());
        transactionRemote.operationFail(JSON.toJSONString(fail));
        transactionRemote.updateTransaction(JSON.toJSONString(transaction));
    }
}
