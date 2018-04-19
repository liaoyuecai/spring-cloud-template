package org.template.cloud.transaction.bean;

import lombok.Data;
import org.template.cloud.transaction.Transaction;

import java.util.Date;

@Data
public class TransactionLog {
    String id;
    /**
     * 当前操作ID
     */
    String operationId;
    String name;
    Integer status;
    Date createTime = new Date();

    public TransactionLog() {
    }

    public TransactionLog(Transaction transaction) {
        this.id = transaction.getId();
        this.name = transaction.getName();
        if (transaction.hasNext())
            this.operationId = transaction.next().getId();
        this.status = transaction.getStatus();
    }
}
