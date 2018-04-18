package org.template.cloud.transaction;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@SpringBootApplication
public class TransactionApplication {
    // 创建事务管理器
    @Bean(name = "txManager")
    public PlatformTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    public static void main(String[] args) {
        SpringApplication.run(TransactionApplication.class, args);
    }
}
