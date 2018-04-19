package org.template.cloud.service.service;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.template.cloud.bean.transaction.Transaction;
import org.template.cloud.bean.transaction.TransactionOperation;
import org.template.cloud.service.remote.TransactionRemote;

@Configuration
public abstract class TransactionService {
    @Autowired
    TransactionRemote transactionRemote;

    /**
     * 通过事务记录表判断当前事务是否是无效事务,无效返回空
     *
     * @param transaction
     * @return
     */
    protected TransactionOperation executable(Transaction transaction) {
        TransactionOperation operation = transaction.next();
        if (transactionRemote.executeTransaction(operation.getTransactionId()) == 0)
            return null;
        if (transactionRemote.executeOperation(operation.getId()) == 0)
            return null;
        return operation;
    }

    /**
     * 当前操作成功执行方法
     * 为保证数据一致,此时只记录操作状态
     *
     * @param transaction
     */
    protected void success(Transaction transaction) {
        TransactionOperation operation = transaction.next();
        operation.success();
        operationLog(operation);
        transaction.complete();
    }


    /**
     * 当前操作失败方法
     * 为保证数据一致,此时只记录操作状态
     *
     * @param transaction
     */
    protected void fail(Transaction transaction, Exception e) {
        TransactionOperation operation = transaction.next();
        operation.fail();
        operationLog(operation);
    }

    /**
     * 记录操作日志
     *
     * @param operation
     */
    private void operationLog(TransactionOperation operation) {
        transactionRemote.updateOperation(JSON.toJSONString(operation));
    }


    protected <T> T getParam(TransactionOperation operation, Class<T> clzz) {
        return JSON.parseObject(operation.getParams(), clzz);
    }
}
