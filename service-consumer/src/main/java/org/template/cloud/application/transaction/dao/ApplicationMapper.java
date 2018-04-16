package org.template.cloud.application.transaction.dao;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


public interface ApplicationMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
