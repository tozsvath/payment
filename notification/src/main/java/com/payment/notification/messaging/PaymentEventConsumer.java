package com.payment.notification.messaging;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.notification.model.ReceiverPaymentEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventConsumer {

  private static final Logger log = LoggerFactory.getLogger(PaymentEventConsumer.class);

  private final ObjectMapper objectMapper;

  public PaymentEventConsumer(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @KafkaListener(topics = "payment-events", groupId = "payment-group")
  public void consume(@Payload String payload, Acknowledgment acknowledgment) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      ReceiverPaymentEvent paymentEvent = objectMapper.readValue(payload,
          ReceiverPaymentEvent.class);
      log.info("Consumed event: {}", paymentEvent);
      acknowledgment.acknowledge();
      log.info("Ackonwledged event: {}", paymentEvent);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }
}
