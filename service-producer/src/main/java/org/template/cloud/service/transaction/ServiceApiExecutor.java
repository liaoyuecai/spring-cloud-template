package org.template.cloud.service.transaction;

import com.alibaba.fastjson.JSON;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.template.cloud.config.ServiceApiConfig;
import org.template.cloud.service.domain.bean.mysql.User;
import org.template.cloud.service.remote.TransactionRemote;
import org.template.cloud.service.service.mysql.UserService;
import org.template.cloud.transaction.bean.TransactionOperation;

@Component
@Log4j
public class ServiceApiExecutor {
    public static final int SUCCESS = 0;
    public static final int EXECUTED = 2;
    public static final int USELESS = 3;

    @Autowired
    UserService userService;
//    @Autowired
//    TransactionService transactionService;

    @Autowired
    TransactionRemote transactionRemote;

    /**
     * 执行事务方法
     * 将状态为未执行或执行中的统一修改为1(执行中),如修改条数为0,说明事务不在执行状态
     * 执行操作之前把当前操作状态由未执行改为执行中,如修改条数为0,说明当前操作已被其他服务执行,则可放弃此事务
     *
     * @param operation
     * @return 0成功;2操作已被其他服务执行,放弃此条事务;3事务不在执行状态,放弃此条事务
     */
    int execute(TransactionOperation operation) {
        int status = operation.getStatus();
        try {
            if (transactionRemote.executeTransaction(operation.getTransactionId()) == 0)
                return USELESS;
            if (transactionRemote.executeOperation(operation.getId()) == 0)
                return EXECUTED;
            operation(operation);
            operation.success();
            return SUCCESS;
        } catch (Exception e) {
            operation.fail();
            throw new RuntimeException(e);
        } finally {
            if (status != operation.getStatus())
                transactionRemote.updateOperation(JSON.toJSONString(operation));
        }
    }

    void operation(TransactionOperation operation) {
        switch (operation.getOperation()) {
            case ServiceApiConfig.USER_INSERT:
                userService.insert(paramTransition(operation.getParams(), User.class));
                break;
            case ServiceApiConfig.USER_UPDATE:
                userService.update(paramTransition(operation.getParams(), User.class));
                break;
        }
    }

    <T> T paramTransition(String param, Class<T> clzz) {
        return JSON.parseObject(param, clzz);
    }
}
