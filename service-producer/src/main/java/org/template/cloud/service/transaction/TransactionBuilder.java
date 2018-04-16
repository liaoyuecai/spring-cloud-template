package org.template.cloud.service.transaction;

import com.alibaba.fastjson.JSON;
import org.template.cloud.service.transaction.bean.TransactionOperation;

public final class TransactionBuilder {
    public static final int NON_EXECUTION = 0;
    public static final int EXECUTING = 1;
    public static final int SUCCESS = 2;
    public static final int FAIL = 3;

    Transaction transaction;

    public TransactionBuilder(String transactionName) {
        transaction = new Transaction();
        transaction.setName(transactionName);
    }

    public TransactionBuilder addOperation(String topic, Integer operation, Object params) {
        TransactionOperation o = new TransactionOperation();
        o.setNo(transaction.getOperations().size() + 1);
        o.setOperation(operation);
        o.setTopic(topic);
        o.setParams(JSON.toJSONString(params));
        transaction.getOperations().add(o);
        return this;
    }

    public TransactionBuilder addOperation(String name, String topic, Integer operation, Object params) {
        TransactionOperation o = new TransactionOperation();
        o.setName(name);
        o.setNo(transaction.getOperations().size() + 1);
        o.setOperation(operation);
        o.setTopic(topic);
        o.setParams(JSON.toJSONString(params));
        transaction.getOperations().add(o);
        return this;
    }

    public Transaction get() {
        return transaction;
    }
}
