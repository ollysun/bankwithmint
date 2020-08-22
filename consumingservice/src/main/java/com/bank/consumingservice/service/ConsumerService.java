package com.bank.consumingservice.service;

import com.bank.consumingservice.model.CardRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ConsumerService {

    @Value("${topic.name}")
    private String consumerTopicName;

    @KafkaListener(topics = "${topic.name}",groupId = "${kafka.listener.group.Id}")
    public void listenTopic(ConsumerRecord<String, CardRequest> cr){
        log.info("received data= {} Topic Name={}", cr.value(), consumerTopicName);

        if (cr.value() != null) {
            log.info(String.valueOf(cr.value()));
        }else{
            log.error("error from queue " + cr.value() + cr.value());
        }
    }

}
