package org.template.cloud.bean.module;

import lombok.Data;

import java.util.UUID;

@Data
public class BankCard {
    String id = UUID.randomUUID().toString();
    String cardId;
    String userId;
    Integer balance;
}
