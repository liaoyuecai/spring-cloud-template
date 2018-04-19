package org.template.cloud.transaction.rabbit;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RabbitConfig {

    //    @Value("transaction-output default transaction-output")
//    String INPUT = "";
    String OUTPUT = "output";

    @Output(OUTPUT)
    MessageChannel output();
}
