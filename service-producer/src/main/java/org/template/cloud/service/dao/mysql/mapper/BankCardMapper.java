package org.template.cloud.service.dao.mysql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.template.cloud.bean.module.BankCard;
import org.template.cloud.bean.module.User;
import org.template.cloud.service.dao.mysql.ServiceMapper;

@Mapper
public interface BankCardMapper extends ServiceMapper<User> {
    int update(BankCard bankCard);
}
