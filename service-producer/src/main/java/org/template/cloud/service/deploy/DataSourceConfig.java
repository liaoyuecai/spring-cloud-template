package org.template.cloud.service.deploy;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {
    @Bean(name = "businessDataSource")//业务数据库
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.business") // application中对应属性的前缀
    public DataSource dataSource1() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "transactionDataSource")//事务数据库
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.transaction") // application中对应属性的前缀
    public DataSource dataSource2() {
        return DataSourceBuilder.create().build();
    }

}
