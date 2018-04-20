package org.template.cloud.service.transaction;

/**
 * Created by Administrator on 2018/4/20 0020.
 */
public interface TransactionTestIn {

    public void test(@MQTransaction(clazz = String.class,paramValue = "aa")String a);
}
