package org.template.cloud.service.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.SubscribableChannel;

public interface RabbitConfig {

    String INPUT = "input";

    @Input(INPUT)
    SubscribableChannel input();
}
