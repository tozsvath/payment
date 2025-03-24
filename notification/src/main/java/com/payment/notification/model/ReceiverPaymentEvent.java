package com.payment.notification.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.payment.notification.serializer.LocalDateTimeDeserializer;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReceiverPaymentEvent(
    UUID transactionId,
    Long senderAccountNumber,
    BigDecimal senderBalance,
    Long recipientAccountNumber,
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    LocalDateTime timestamp,
    PaymentStatus status,
    UUID correlationId
) {

}
