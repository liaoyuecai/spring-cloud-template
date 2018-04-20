package org.template.cloud.service.service.mysql.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.template.cloud.bean.module.BankCard;
import org.template.cloud.service.dao.mysql.mapper.BankCardMapper;
import org.template.cloud.service.service.mysql.BankCardService;

@Service("bankCardService")
@Transactional("txManager")
public class BankCardServiceImpl implements BankCardService {

    @Autowired
    BankCardMapper bankCardMapper;

    @Override
    public int update(BankCard bankCard) {
        return bankCardMapper.update(bankCard);
    }
}
