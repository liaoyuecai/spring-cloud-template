package org.template.cloud.application.transaction.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.template.cloud.application.transaction.Transaction;
import org.template.cloud.application.transaction.bean.TransactionLog;
import org.template.cloud.application.transaction.dao.TransactionFailMapper;
import org.template.cloud.application.transaction.dao.TransactionLogMapper;
import org.template.cloud.application.transaction.dao.TransactionOperationMapper;
import org.template.cloud.application.transaction.service.TransactionService;

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
    public void createTransaction(Transaction transaction) {
        TransactionLog log = new TransactionLog(transaction);
        transactionLogMapper.save(log);
        transactionOperationMapper.saveList(transaction.getOperations());
    }
}
