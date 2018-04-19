package org.template.cloud.bean.transaction;

import lombok.Data;

@Data
public class FailView {
    String failId;
    String transactionId;
    String transactionName;
    String operationId;
    String topic;
    String operationName;
    String failCause;
    String failTime;
}
