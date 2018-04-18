package org.template.cloud.transaction;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.template.cloud.transaction.service.TransactionService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@EnableScheduling
public class TransactionManager implements SchedulingConfigurer {
    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Autowired
    TransactionService transactionService;


    @Value("${transaction.task.check-interval}")
    String checkInterval;
    @Value("${transaction.task.clean-interval}")
    String cleanInterval;
    @Value("${transaction.task.save-date}")
    Integer saveDate;

    Runnable checkTask = new Runnable() {
        @Override
        public void run() {
            List<Transaction> list = transactionService.getNonExecutionTransactions();
            for (Transaction t : list)
                rabbitTemplate.convertAndSend(t.topic(), JSON.toJSONString(t));
        }
    };

    Runnable cleanTask = new Runnable() {
        @Override
        public void run() {
            LocalDate date = LocalDate.now().minus(saveDate, ChronoUnit.DAYS);
            transactionService.clean(date);
        }
    };

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        Trigger check = new CronTrigger(checkInterval);
        Trigger clean = new CronTrigger(cleanInterval);
        scheduledTaskRegistrar.addTriggerTask(checkTask, check);
        scheduledTaskRegistrar.addTriggerTask(cleanTask, clean);
    }
}
