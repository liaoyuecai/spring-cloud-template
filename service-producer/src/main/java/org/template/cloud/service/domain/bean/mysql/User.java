package org.template.cloud.service.domain.bean.mysql;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private String id = UUID.randomUUID().toString();
    private String userName;
    private String password;
}
