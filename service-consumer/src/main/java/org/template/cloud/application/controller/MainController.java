package org.template.cloud.application.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.template.cloud.application.bean.User;
import org.template.cloud.application.remote.ServiceRemote;
import org.template.cloud.application.remote.TransactionRemote;
import org.template.cloud.config.ServiceApiConfig;
import org.template.cloud.transaction.Transaction;

import java.util.Date;

@Controller
@RequestMapping("/")
public class MainController {
    @Autowired
    ServiceRemote remote;

    @RequestMapping("/hello/{name}")
    @ResponseBody
    @HystrixCommand(fallbackMethod = "fallback_test")
    public String index(@PathVariable("name") String name) {
        return remote.hello(name);
    }

    @Autowired
    private AmqpTemplate rabbitTemplate;
    @Autowired
    private TransactionRemote transactionRemote;

    @RequestMapping("/produce")
    @ResponseBody
    public String produce() {
        String context = "hello " + new Date();
        this.rabbitTemplate.convertAndSend("hello", context);
        this.rabbitTemplate.convertAndSend("test", context);
        this.rabbitTemplate.convertAndSend("serviceQueue", context);
        return "ok";
    }
    @RequestMapping("/transaction")
    @ResponseBody
    public String transaction() {
        User user = new User();
        user.setPassword("122333");
        user.setUserName("lixiaolong");
        Transaction transaction = new Transaction().
                addOperation("serviceQueue", ServiceApiConfig.USER_INSERT, user);
        transaction.setName("test");
        user.setPassword("askhdalk");
        transaction.addOperation("userUpdate", "serviceQueue", ServiceApiConfig.USER_UPDATE, user);
        transactionRemote.transaction(JSON.toJSONString(transaction));
        return "ok";
    }



    /**
     * 熔断器失败返回
     * 这里的参数必须与熔断方法参数一致
     *
     * @return
     */
    public String fallback_test(String name) {
        return "资源损坏或资源紧张";
    }


}
