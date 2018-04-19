package org.template.cloud.service.transaction;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.template.cloud.bean.constant.ServerApiProperties;
import org.template.cloud.bean.transaction.Transaction;
import org.template.cloud.service.service.mysql.UserService;

@Component
@Log4j
public class ServiceApiExecutor {

    @Autowired
    UserService userService;

    /**
     * 执行事务方法
     * 根据标识执行不同方法,返回执行结果
     *
     * @param transaction
     * @return
     */
    boolean execute(Transaction transaction) {
        switch (transaction.next().getOperation()) {
            case ServerApiProperties.USER_INSERT:
                return userService.insert(transaction);
            case ServerApiProperties.USER_UPDATE:
                return userService.update(transaction);
        }
        return false;
    }

}
