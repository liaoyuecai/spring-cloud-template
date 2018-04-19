package org.template.cloud.application.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.template.cloud.bean.constant.ServerApiProperties;
import org.template.cloud.bean.module.User;
import org.template.cloud.application.remote.ServiceRemote;
import org.template.cloud.application.remote.TransactionRemote;
import org.template.cloud.bean.transaction.Transaction;

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

    /**
     * get请求/用于测试事务
     * 构建了一个事务Transaction
     * Transaction(事务)中包含事务的名称,状态(未执行,执行中,执行完毕,执行失败),操作列表(TransactionOperation)
     * TransactionOperation(操作)中包含当前操作的名称,需要操作的模块/服务/队列(topic),操作标识,用于判断操作方法,操作参数和序号
     * 通过transactionRemote远程调用transaction服务的接口,此接口主要将事务提交到mq
     * 提交mq时会使用Transaction中排序最靠前的TransactionOperation的topic作为队列名,这个队列名对应模块的服务名,
     * 模块也会使用相同名称去mq获取当前服务需要进行的事务操作,操作完毕后,如操作成功将当前TransactionOperation从Transaction
     * 中移除,并判断是否需要继续执行该事务,如需继续执行则重复提交mq操作
     * 操作失败则中断事务
     * @return
     */
    @RequestMapping("/transaction")
    @ResponseBody
    public String transaction() {
        User user = new User();
        user.setPassword("122333");
        user.setUserName("lixiaolong");
        Transaction transaction = new Transaction().
                addOperation("serviceQueue", ServerApiProperties.USER_INSERT, user);
        transaction.setName("test");
        user.setPassword("askhdalk");
        transaction.addOperation("userUpdate", "serviceQueue", ServerApiProperties.USER_UPDATE, user);
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
