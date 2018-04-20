package org.template.cloud.application.rabbit;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface RabbitConfig {

    String INPUT = "input";

    @Input(INPUT)
    SubscribableChannel input();
}
