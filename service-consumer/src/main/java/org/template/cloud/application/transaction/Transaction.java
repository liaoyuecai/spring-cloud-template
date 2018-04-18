package org.template.cloud.application.transaction;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.template.cloud.application.transaction.bean.TransactionOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Transaction {

    public static final int NON_EXECUTION = 0;
    public static final int EXECUTING = 1;
    public static final int SUCCESS = 2;
    public static final int FAIL = 3;

    String id = UUID.randomUUID().toString();
    volatile List<TransactionOperation> operations = new ArrayList();
    int status = NON_EXECUTION;
    String name;

    public Transaction addOperation(String topic, Integer operation, Object params) {
        TransactionOperation o = new TransactionOperation();
        o.setNo(operations.size() + 1);
        o.setOperation(operation);
        o.setTopic(topic);
        o.setParams(JSON.toJSONString(params));
        o.setTransactionId(id);
        operations.add(o);
        return this;
    }

    public Transaction addOperation(String name, String topic, Integer operation, Object params) {
        TransactionOperation o = new TransactionOperation();
        o.setName(name);
        o.setNo(operations.size() + 1);
        o.setOperation(operation);
        o.setTopic(topic);
        o.setParams(JSON.toJSONString(params));
        o.setTransactionId(id);
        operations.add(o);
        return this;
    }

    public TransactionOperation next() {
        return operations.get(0);
    }

    public String topic() {
        return operations.get(0).getTopic();
    }
}
