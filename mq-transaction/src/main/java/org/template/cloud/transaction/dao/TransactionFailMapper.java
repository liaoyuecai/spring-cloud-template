package org.template.cloud.transaction.dao;

import org.apache.ibatis.annotations.Mapper;
import org.template.cloud.transaction.bean.FailView;
import org.template.cloud.transaction.bean.TransactionFail;

import java.util.List;

@Mapper
public interface TransactionFailMapper extends ApplicationMapper<TransactionFail> {
    List<FailView> findAll();
}
