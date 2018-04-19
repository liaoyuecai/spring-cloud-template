package org.template.cloud.bean.transaction;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class TransactionFail {
    String id = UUID.randomUUID().toString();
    String transactionId;
    String operationId;
    String failCause;
    Date createTime = new Date();
}
