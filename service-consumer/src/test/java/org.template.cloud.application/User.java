package org.template.cloud.application;

import lombok.Data;

import java.util.UUID;

@Data
public class User {
    private String id = UUID.randomUUID().toString();
    private String userName;
    private String password;
}
