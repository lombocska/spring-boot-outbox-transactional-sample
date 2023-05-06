package io.lombocska.listener.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PsqlPushNotificationListenerService {

    @Value("${application.kafka.cat-topic}")
    private String catTopic;

    @Value("${application.kafka.dog-topic}")
    private String dogTopic;

    private final KafkaDispatcher dispatcher;

    @Bean
    IntegrationFlow streamCatMessage(MessageChannel catSubscriptionChannel) {
         return IntegrationFlow
                .from(catSubscriptionChannel)
                .handle(message -> {
                    log.info("Getting the cat message with id {}", message.getHeaders().getId());
                    dispatcher.send(message, catTopic);
                })
                .get();
    }

    @Bean
    IntegrationFlow streamDogMessage(MessageChannel dogSubscriptionChannel) {
         return IntegrationFlow
                .from(dogSubscriptionChannel)
                 .handle(message -> {
                     log.debug("Getting the dog message " + message.getPayload());
                     dispatcher.send(message, dogTopic);
                 })
                 .get();
    }
}
