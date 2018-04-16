package org.template.cloud.application.transaction.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.template.cloud.application.transaction.bean.TransactionLog;
import org.template.cloud.application.transaction.dao.ApplicationMapper;

import java.util.List;
@Mapper
public interface TransactionLogMapper extends ApplicationMapper<TransactionLog> {
    int save(TransactionLog t);

    int saveList(@Param(value = "list") List<TransactionLog> list);

    int update(TransactionLog t);
}
