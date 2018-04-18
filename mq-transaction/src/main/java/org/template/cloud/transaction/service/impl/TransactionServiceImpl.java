package org.template.cloud.transaction.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.template.cloud.transaction.Transaction;
import org.template.cloud.transaction.bean.TransactionLog;
import org.template.cloud.transaction.bean.TransactionOperation;
import org.template.cloud.transaction.dao.TransactionFailMapper;
import org.template.cloud.transaction.dao.TransactionLogMapper;
import org.template.cloud.transaction.dao.TransactionOperationMapper;
import org.template.cloud.transaction.service.TransactionService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional("txManager")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionFailMapper transactionFailMapper;
    @Autowired
    TransactionLogMapper transactionLogMapper;
    @Autowired
    TransactionOperationMapper transactionOperationMapper;

    @Override
    public List<Transaction> getNonExecutionTransactions() {
        List<TransactionLog> logs = transactionLogMapper.getAllNonExecution();
        if (logs.isEmpty())
            return new ArrayList<>();
        List<TransactionOperation> operations = transactionOperationMapper.getAllNonExecution(logs);
        Map<String, Transaction> map = new HashMap<>();
        Transaction transaction;
        for (TransactionLog l : logs) {
            transaction = new Transaction();
            transaction.setId(l.getId());
            transaction.setName(l.getName());
            transaction.setStatus(l.getStatus());
            map.put(l.getId(), transaction);
        }
        for (TransactionOperation o : operations) {
            transaction = map.get(o.getTransactionId());
            transaction.addOperation(o);
        }
        return new ArrayList<>(map.values());
    }

    @Override
    public void clean(LocalDate date) {
        transactionLogMapper.deleteByDate(date);
        transactionOperationMapper.deleteByDate(date);
    }

}
