package org.template.cloud.transaction.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.template.cloud.transaction.bean.TransactionLog;
import org.template.cloud.transaction.bean.TransactionOperation;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface TransactionOperationMapper extends ApplicationMapper<TransactionOperation> {

    int save(TransactionOperation t);

    int saveList(@Param(value = "list") List<TransactionOperation> list);

    int update(TransactionOperation t);

    List<TransactionOperation> getAllNonExecution(@Param(value = "list") List<TransactionLog> list);

    List<TransactionOperation> getAllOperations(@Param(value = "list") List<TransactionLog> list);

    List<TransactionOperation> getByTransactionId(String id);

    int deleteByDate(LocalDate date);

    int executing(String id);

    int getStatusById(String id);
}
