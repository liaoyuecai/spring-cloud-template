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
@MapperScan(basePackages = {"org.template.cloud.service.dao.mysql.mapper"}, sqlSessionFactoryRef = "businessFactory")
public class BusinessDataSourceConfig {
    @Autowired
    @Qualifier("businessDataSource")
    private DataSource dataSource;


    @Bean(name = "businessFactory")
    public SqlSessionFactory businessFactory() throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setConfigLocation(new ClassPathResource("mybatis-config.xml"));
            bean.setMapperLocations(resolver.getResources("classpath*:mapper/business/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean(name = "businessTemplate")
    public SqlSessionTemplate businessTemplate() throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(businessFactory());
        return template;
    }
}
