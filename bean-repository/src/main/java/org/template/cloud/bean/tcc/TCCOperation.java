package org.template.cloud.bean.tcc;

import lombok.Data;

import java.util.UUID;

@Data
public class TCCOperation {
    String id = UUID.randomUUID().toString();
    String server;
    String service;
    String method;
    String params;

    public TCCOperation(String server, String service, String method, String params) {
        this.server = server;
        this.service = service;
        this.method = method;
        this.params = params;
    }
}
