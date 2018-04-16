package org.template.cloud.service.deploy;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"org.template.cloud.service.transaction.dao.mapper"},
        sqlSessionFactoryRef = "transactionFactory")
public class TransactionDataSourceConfig {
    @Autowired
    @Qualifier("transactionDataSource")
    private DataSource dataSource;


    @Bean(name = "transactionFactory")
    public SqlSessionFactory transactionFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //添加Mapper XML目录及配置文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
            bean.setMapperLocations(resolver.getResources("classpath*:mapper/transaction/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "transactionTemplate")
    public SqlSessionTemplate transactionTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(transactionFactory());
        return template;
    }
}
