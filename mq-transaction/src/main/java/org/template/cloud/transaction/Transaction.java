package org.template.cloud.transaction;

import lombok.Data;
import org.template.cloud.transaction.bean.TransactionOperation;

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

    public Transaction addOperation(TransactionOperation operation) {
        operations.add(operation);
        return this;
    }

    public TransactionOperation next() {
        return operations.get(0).execute();
    }

    public String topic() {
        return operations.get(0).getTopic();
    }
}
