package org.template.cloud.bean.transaction;

import lombok.Data;
import org.template.cloud.bean.constant.ConstantProperties;

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
    Integer status = ConstantProperties.TRANSACTION_NON_EXECUTION;
    Date createTime = new Date();

    public TransactionOperation execute() {
        status = ConstantProperties.TRANSACTION_SUCCESS;
        return this;
    }

    public void success() {
        status = ConstantProperties.TRANSACTION_SUCCESS;
    }

    public void fail() {
        status = ConstantProperties.TRANSACTION_FAIL;
    }
}
