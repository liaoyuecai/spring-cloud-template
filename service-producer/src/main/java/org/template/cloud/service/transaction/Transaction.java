package org.template.cloud.service.transaction;

import lombok.Data;
import org.template.cloud.service.transaction.bean.TransactionOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public final class Transaction {
    public static final int NON_EXECUTION = 0;
    public static final int EXECUTING = 1;
    public static final int SUCCESS = 2;
    public static final int FAIL = 3;
    String id = UUID.randomUUID().toString();
    volatile List<TransactionOperation> operations = new ArrayList();
    int status = NON_EXECUTION;
    String name;

    public boolean hasNext() {
        return operations.size() > 0;
    }

    public TransactionOperation next() {
        return operations.get(0);
    }

    public String topic() {
        return operations.get(0).getTopic();
    }

    void complete() {
        operations.remove(0);
        if (operations.isEmpty())
            status = SUCCESS;
    }

    String defeated() {
        status = FAIL;
        return operations.get(0).getId();
    }

}
