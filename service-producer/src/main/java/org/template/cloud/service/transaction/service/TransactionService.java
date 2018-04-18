package org.template.cloud.service.transaction.service;

import org.template.cloud.service.transaction.Transaction;
import org.template.cloud.service.transaction.bean.TransactionFail;
import org.template.cloud.service.transaction.bean.TransactionOperation;

public interface TransactionService {
    void updateTransaction(Transaction transaction);

    void updateOperation(TransactionOperation operation);

    void operationFail(TransactionFail fail);

//    int getTransactionStatus(String transactionId);

    int executeOperation(String operationId);

    int executeTransaction(String transactionId);

    int getOperationStatus(String operationId);
}
