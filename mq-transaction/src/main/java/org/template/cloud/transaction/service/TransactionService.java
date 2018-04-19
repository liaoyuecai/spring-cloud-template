package org.template.cloud.transaction.service;


import org.template.cloud.transaction.Transaction;
import org.template.cloud.transaction.bean.TransactionFail;
import org.template.cloud.transaction.bean.TransactionOperation;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    void updateTransaction(Transaction transaction);

    void updateOperation(TransactionOperation operation);

    void operationFail(TransactionFail fail);

    void createTransaction(Transaction transaction);

    int executeOperation(String operationId);

    int executeTransaction(String transactionId);

    int getOperationStatus(String operationId);
    List<Transaction> getNonExecutionTransactions();

    void clean(LocalDate date);
}
