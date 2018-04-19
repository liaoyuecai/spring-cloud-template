package org.template.cloud.transaction.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.template.cloud.transaction.bean.FailView;
import org.template.cloud.transaction.bean.TransactionFail;

import java.util.List;

@Mapper
public interface TransactionFailMapper extends ApplicationMapper<TransactionFail> {
    int save(TransactionFail t);

    int saveList(@Param(value = "list") List<TransactionFail> list);
    List<FailView> findAll();
}
