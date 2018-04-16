package org.template.cloud.service.transaction.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.template.cloud.service.transaction.bean.TransactionFail;
import org.template.cloud.service.transaction.dao.TransactionMapper;

import java.util.List;

public interface TransactionFailMapper extends TransactionMapper<TransactionFail> {
    int save(TransactionFail t);

    int saveList(@Param(value = "list") List<TransactionFail> list);
}
