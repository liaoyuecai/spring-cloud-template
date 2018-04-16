package org.template.cloud.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@EnableDiscoveryClient
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})//禁止自动配置单数据源
public class ServiceApplication {


    /**
     * 针对事务记录数据库的事务管理器
     * 其他数据库使用mq做分布式事务控制,不单独使用事务控制器
     *
     * @param dataSource
     * @return
     */
    @Bean(name = "tdManager")
    public PlatformTransactionManager txManager(@Qualifier("transactionDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


    /**
     * 负载均衡配置
     *
     * @return
     */
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        RestTemplate template = new RestTemplate();
        SimpleClientHttpRequestFactory factory = (SimpleClientHttpRequestFactory) template.getRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(3000);
        return template;
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }
}
