package org.template.cloud.bean.transaction;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.template.cloud.bean.constant.ConstantProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Transaction {


    String id = UUID.randomUUID().toString();
    volatile List<TransactionOperation> operations = new ArrayList();
    int status = ConstantProperties.TRANSACTION_NON_EXECUTION;
    String name;

    public Transaction addOperation(TransactionOperation operation) {
        operations.add(operation);
        return this;
    }

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

    public boolean hasNext() {
        return operations.size() > 0;
    }

    public void complete() {
        operations.remove(0);
        if (operations.isEmpty())
            status = ConstantProperties.TRANSACTION_SUCCESS;
    }

    public String defeated() {
        status = ConstantProperties.TRANSACTION_FAIL;
        if (this.hasNext())
            return operations.get(0).getId();
        return "";
    }

    public TransactionOperation next() {
        return operations.get(0);
    }

    public String topic() {
        return operations.get(0).getTopic();
    }
}
