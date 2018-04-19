package org.template.cloud.service.rabbit;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding(RabbitConfig.class)
public class RabbitConsumer {
    @StreamListener(RabbitConfig.INPUT)
    public void receive(String payload) {
        System.out.println("接收到MQ消息:" + payload);
    }
}
