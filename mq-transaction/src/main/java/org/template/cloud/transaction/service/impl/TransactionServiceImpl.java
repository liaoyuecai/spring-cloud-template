package org.template.cloud.transaction.service.impl;


import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.template.cloud.transaction.Transaction;
import org.template.cloud.transaction.bean.TransactionFail;
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

    @Autowired
    private AmqpTemplate rabbitTemplate;

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
    public void createTransaction(Transaction transaction) {
        TransactionLog log = new TransactionLog(transaction);
        transactionLogMapper.save(log);
        transactionOperationMapper.saveList(transaction.getOperations());
        rabbitTemplate.convertAndSend(transaction.topic(), JSON.toJSONString(transaction));
    }

    @Override
    public void clean(LocalDate date) {
        transactionLogMapper.deleteByDate(date);
        transactionOperationMapper.deleteByDate(date);
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        TransactionLog log = new TransactionLog(transaction);
        transactionLogMapper.update(log);
    }

    @Override
    public void updateOperation(TransactionOperation operation) {
        transactionOperationMapper.update(operation);
    }

    @Override
    public void operationFail(TransactionFail fail) {
        transactionFailMapper.save(fail);
    }

    @Override
    public int executeOperation(String operationId) {
        return transactionOperationMapper.executing(operationId);
    }

    @Override
    public int executeTransaction(String transactionId) {
        return transactionLogMapper.execute(transactionId);
    }

    @Override
    public int getOperationStatus(String operationId) {
        return transactionOperationMapper.getStatusById(operationId);
    }


}
