package com.bank.card.service;

import com.bank.card.request.CardRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Slf4j
@Component
public class ProducerService {
    @Autowired
    private KafkaTemplate<String, CardRequest> template;

    @Value("${topic.name}")
    private String topicName;

    public void produce(CardRequest cardRequest) {
        Message<CardRequest> message = MessageBuilder
                .withPayload(cardRequest)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .build();

        ListenableFuture<SendResult<String, CardRequest>> future = template.send(message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, CardRequest>>() {
            @Override
            public void onSuccess(SendResult<String, CardRequest> sendResult) {
                log.info("Sent {}: ", sendResult.getProducerRecord().value());
                log.info("Topic Name {}", sendResult.getRecordMetadata().topic() + ": " + sendResult.getRecordMetadata());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error("kafka error " + ex.getMessage());
            }
        });
    }

}
