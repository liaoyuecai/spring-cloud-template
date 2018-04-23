package org.template.cloud.tcc.client;

import lombok.Data;

import java.util.UUID;

@Data
public class InformMessage {
    final static int EXCEPTION = 0;
    final static int EXECUTE = 1;
    final static int EXECUTE_SUCCESS = 2;
    final static int EXECUTE_FAIL = 3;
    final static int ACK = 4;
    final static int COMMIT = 5;
    final static int ROLLBACK = 6;

    Integer type;
    String id = UUID.randomUUID().toString();
    String operateId;
    String service;
    String method;
    String params;

    public InformMessage(Integer type, String operateId, String service, String method, String params) {
        this.type = type;
        this.operateId = operateId;
        this.service = service;
        this.method = method;
        this.params = params;
    }

    public InformMessage() {
    }
}
