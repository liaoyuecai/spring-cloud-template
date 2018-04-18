package org.template.cloud.service.transaction.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.template.cloud.service.transaction.Transaction;
import org.template.cloud.service.transaction.bean.TransactionFail;
import org.template.cloud.service.transaction.bean.TransactionLog;
import org.template.cloud.service.transaction.bean.TransactionOperation;
import org.template.cloud.service.transaction.dao.mapper.TransactionFailMapper;
import org.template.cloud.service.transaction.dao.mapper.TransactionLogMapper;
import org.template.cloud.service.transaction.dao.mapper.TransactionOperationMapper;
import org.template.cloud.service.transaction.service.TransactionService;

@Service
@Transactional("tdManager")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionFailMapper transactionFailMapper;
    @Autowired
    TransactionLogMapper transactionLogMapper;
    @Autowired
    TransactionOperationMapper transactionOperationMapper;

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

//    @Override
//    public int getTransactionStatus(String transactionId) {
//        return transactionLogMapper.getStatusById(transactionId);
//    }

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
