package org.template.cloud.application.transaction.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.template.cloud.application.transaction.bean.TransactionFail;
import org.template.cloud.application.transaction.dao.ApplicationMapper;

import java.util.List;
@Mapper
public interface TransactionFailMapper extends ApplicationMapper<TransactionFail> {
    int save(TransactionFail t);

    int saveList(@Param(value = "list") List<TransactionFail> list);
}
