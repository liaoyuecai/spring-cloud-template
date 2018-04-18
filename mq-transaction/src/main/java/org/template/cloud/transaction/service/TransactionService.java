package org.template.cloud.transaction.service;


import org.template.cloud.transaction.Transaction;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    List<Transaction> getNonExecutionTransactions();

    void clean(LocalDate date);
}
