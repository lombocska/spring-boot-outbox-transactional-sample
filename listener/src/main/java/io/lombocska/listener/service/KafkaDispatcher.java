package io.lombocska.listener.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "kafkaTransactionManager")
public class KafkaDispatcher {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void send(final Message<?> message, final String topic) {
        try {
            String id = Objects.requireNonNull(message.getHeaders().get("id")).toString();
            String payload = serialize(message.getPayload());
            this.kafkaTemplate.send(topic, id, payload);
            log.debug("Message with id {} has been sent to the topic: {}", id, topic);
        } catch (NullPointerException npe) {
            log.warn("Skipping message since missing id. Payload: {}", message.getPayload());
        }
    }

    private String serialize(final Object payload) {
        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error has occurred while serializing message.");
        }
    }

}
