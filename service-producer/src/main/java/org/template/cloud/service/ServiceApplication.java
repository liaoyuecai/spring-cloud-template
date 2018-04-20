package org.template.cloud.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.client.RestTemplate;
import org.template.cloud.bean.module.BankCard;
import org.template.cloud.service.transaction.tcc.SpringUtil;
import org.template.cloud.service.transaction.tcc.TccManager;

import javax.sql.DataSource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients
public class ServiceApplication {


    @Bean(name = "txManager")
    public PlatformTransactionManager txManager(@Qualifier("dataSource") DataSource dataSource) {
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
        TccManager.init("org.template.cloud.service.service");
        try {
            String serviceName = "bankCardService";
            String methodName = "update";
            List<Object> list = new ArrayList();
            BankCard card = new BankCard();
            card.setId("1");
            card.setUserId("1");
            card.setBalance(10);
            list.add(card);
            Object service = TccManager.serviceMap.get(serviceName);
            Class[] classes = TccManager.paramMap.get(serviceName + "." + methodName);
            Transactional t = service.getClass().getAnnotation(Transactional.class);
            DataSourceTransactionManager manager = (DataSourceTransactionManager) SpringUtil.getBean(t.value());
            if (classes == null) {
                Method execute = service.getClass().getDeclaredMethod(methodName);
                execute.invoke(service);
            } else {
                String params = JSON.toJSONString(list);
                JSONArray paramList = JSON.parseArray(params);
                int m = paramList.size();
                Object[] params1 = new Object[paramList.size()];
                for (int i = 0; i < m; i++) {
                    params1[i] = paramList.getObject(i, classes[i]);
                }
//                List<Object> paramList = JSON.parseObject(params, List.class);
                Method execute = service.getClass().getDeclaredMethod(methodName, classes);
                DefaultTransactionDefinition def = new DefaultTransactionDefinition();
                def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
                TransactionStatus status = manager.getTransaction(def);
                execute.invoke(service, new Object[]{card});
                manager.commit(status);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
