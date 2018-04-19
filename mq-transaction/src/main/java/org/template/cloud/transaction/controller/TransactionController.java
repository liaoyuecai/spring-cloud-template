package org.template.cloud.transaction.controller;


import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.template.cloud.transaction.Transaction;
import org.template.cloud.transaction.bean.TransactionFail;
import org.template.cloud.transaction.bean.TransactionOperation;
import org.template.cloud.transaction.service.TransactionService;

@RestController
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @RequestMapping(value = "/transaction", method = RequestMethod.POST)
    public String transaction(String transaction) {
        transactionService.createTransaction(stringToObject(transaction, Transaction.class));
        return "";
    }

    @RequestMapping(value = "/executeOperation", method = RequestMethod.POST)
    public Integer executeOperation(String operationId) {
        return transactionService.executeOperation(operationId);
    }

    @RequestMapping(value = "/executeTransaction", method = RequestMethod.POST)
    public Integer executeTransaction(String transactionId) {
        return transactionService.executeTransaction(transactionId);
    }

    @RequestMapping(value = "/getOperationStatus", method = RequestMethod.POST)
    public Integer getOperationStatus(String operationId) {
        return transactionService.getOperationStatus(operationId);
    }

    @RequestMapping(value = "/updateTransaction", method = RequestMethod.POST)
    public String updateTransaction(String transaction) {
        transactionService.updateTransaction(stringToObject(transaction, Transaction.class));
        return "";
    }

    @RequestMapping(value = "/updateOperation", method = RequestMethod.POST)
    public String updateOperation(String operation) {
        transactionService.updateOperation(stringToObject(operation, TransactionOperation.class));
        return "";
    }

    @RequestMapping(value = "/operationFail", method = RequestMethod.POST)
    public String operationFail(String fail) {
        transactionService.operationFail(stringToObject(fail, TransactionFail.class));
        return "";
    }

    final <T> T stringToObject(String str, Class<T> clazz) {
        return JSON.parseObject(str, clazz);
    }

}
