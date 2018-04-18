package org.template.cloud.transaction.dao;

import org.apache.ibatis.annotations.Mapper;
import org.template.cloud.transaction.bean.TransactionLog;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TransactionLogMapper extends ApplicationMapper<TransactionLog> {
    List<TransactionLog> getAllNonExecution();

    int deleteByDate(LocalDate date);
}
