package org.template.cloud.service.transaction.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.template.cloud.service.transaction.bean.TransactionOperation;
import org.template.cloud.service.transaction.dao.TransactionMapper;

import java.util.List;

public interface TransactionOperationMapper extends TransactionMapper<TransactionOperation> {
    int save(TransactionOperation t);

    int saveList(@Param(value = "list") List<TransactionOperation> list);

    int update(TransactionOperation t);
}
