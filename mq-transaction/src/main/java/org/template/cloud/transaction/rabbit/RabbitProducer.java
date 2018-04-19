package org.template.cloud.transaction.rabbit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

@EnableBinding(RabbitConfig.class)
public class RabbitProducer {
    @Autowired
    @Output(RabbitConfig.OUTPUT)
    private MessageChannel channel;

    public void send(String message) {
        channel.send(MessageBuilder.withPayload(message).build());
    }
}
