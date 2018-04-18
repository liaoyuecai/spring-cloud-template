package org.template.cloud.application.transaction.bean;

import lombok.Data;
import org.template.cloud.application.transaction.Transaction;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Data
public class TransactionOperation implements Serializable {
    String id = UUID.randomUUID().toString();
    /**
     * 序号
     */
    Integer no;
    String transactionId;
    /**
     * mq队列名
     */
    String topic;
    /**
     * 操作名
     */
    String name;
    /**
     * 操作标识
     */
    Integer operation;
    String params;
    Integer status = Transaction.NON_EXECUTION;
    Date createTime = new Date();


    public void success() {
        status = Transaction.SUCCESS;
    }

    public void fail() {
        status = Transaction.FAIL;
    }
}
