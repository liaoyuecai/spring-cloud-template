package org.template.cloud.service.transaction.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.template.cloud.service.transaction.bean.TransactionLog;
import org.template.cloud.service.transaction.dao.TransactionMapper;

import java.util.List;

public interface TransactionLogMapper extends TransactionMapper<TransactionLog> {
    int save(TransactionLog t);

    int saveList(@Param(value = "list") List<TransactionLog> list);

    int update(TransactionLog t);

    int getStatusById(String id);

    int execute(String id);
}
