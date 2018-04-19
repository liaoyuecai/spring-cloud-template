package org.template.cloud.transaction.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.template.cloud.transaction.bean.TransactionLog;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TransactionLogMapper extends ApplicationMapper<TransactionLog> {
    int save(TransactionLog t);

    int saveList(@Param(value = "list") List<TransactionLog> list);

    int update(TransactionLog t);

    int getStatusById(String id);

    int execute(String id);

    List<TransactionLog> getAllNonExecution();

    int deleteByDate(LocalDate date);
}
