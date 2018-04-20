package org.template.cloud.service.transaction.tcc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

@Component
public class TccConfig {

//    @Autowired
//    DataSourceTransactionManager txManager;
    @Autowired
    private void test() {
        System.out.println(111);
//        TccManager.init("org.template.cloud.service.service");
//        TccManager.addTransactionManager("txManager", txManager);
    }
}
