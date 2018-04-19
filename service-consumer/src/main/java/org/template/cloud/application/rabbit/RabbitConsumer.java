package org.template.cloud.application.rabbit;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

@EnableBinding(RabbitConfig.class)
public class RabbitConsumer {
    @StreamListener(RabbitConfig.INPUT)
    public void receive(String payload) {
        System.out.println("接收到MQ消息:" + payload);
    }
}
