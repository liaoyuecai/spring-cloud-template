package org.template.cloud.application.transaction.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.template.cloud.application.transaction.bean.TransactionOperation;
import org.template.cloud.application.transaction.dao.ApplicationMapper;

import java.util.List;

@Mapper
public interface TransactionOperationMapper extends ApplicationMapper<TransactionOperation> {
    int save(TransactionOperation t);

    int saveList(@Param(value = "list") List<TransactionOperation> list);

    int update(TransactionOperation t);
}
