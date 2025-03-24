package com.payment.transaction.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.transaction.model.messaging.PaymentEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PaymentEventProducer {

  private static final String TOPIC_NAME = "payment-events";
  private static final String DEAD_LETTER_TOPIC = "payment-dead-letter-events";
  private final KafkaTemplate<String, String> kafkaTemplate;
  private final ObjectMapper objectMapper;


  public PaymentEventProducer(KafkaTemplate<String, String> kafkaTemplate,
      ObjectMapper objectMapper) {
    this.kafkaTemplate = kafkaTemplate;
    this.objectMapper = objectMapper;
  }

  public void sendPaymentEvent(PaymentEvent event) {
    try {
      String eventJson = objectMapper.writeValueAsString(event);
      kafkaTemplate.send(TOPIC_NAME, eventJson);
      log.info("Payment event sent to Kafka: {}", event);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
