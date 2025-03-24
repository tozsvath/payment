package com.payment.transaction_service.model.messaging;

import com.payment.transaction_service.model.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentEvent(
    UUID transactionId,
    Long senderAccountNumber,
    BigDecimal senderBalance,
    Long recipientAccountNumber,
    LocalDateTime timestamp,
    PaymentStatus status,
    UUID correlationId
) {

}