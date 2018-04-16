package org.template.cloud.application.transaction;

import lombok.Data;
import org.template.cloud.application.transaction.bean.TransactionOperation;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class Transaction {
    String id = UUID.randomUUID().toString();
    volatile List<TransactionOperation> operations = new ArrayList();
    int status = TransactionBuilder.NON_EXECUTION;
    String name;

    public TransactionOperation next() {
        return operations.get(0).execute();
    }

    void complete() {
        operations.get(0).success();
        operations.remove(0);
        if (operations.isEmpty())
            status = TransactionBuilder.SUCCESS;
    }

    void defeated() {
        operations.get(0).fail();
        status = TransactionBuilder.FAIL;
    }

}
