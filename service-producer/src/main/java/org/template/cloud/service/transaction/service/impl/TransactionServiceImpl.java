package org.template.cloud.service.transaction.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.template.cloud.service.transaction.dao.mapper.TransactionFailMapper;
import org.template.cloud.service.transaction.dao.mapper.TransactionLogMapper;
import org.template.cloud.service.transaction.dao.mapper.TransactionOperationMapper;
import org.template.cloud.service.transaction.bean.TransactionLog;
import org.template.cloud.service.transaction.service.TransactionService;
import org.template.cloud.service.transaction.Transaction;

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
    public void createTransaction(Transaction transaction) {
        TransactionLog log = new TransactionLog(transaction);
        transactionLogMapper.save(log);
        transactionOperationMapper.saveList(transaction.getOperations());
    }
}
