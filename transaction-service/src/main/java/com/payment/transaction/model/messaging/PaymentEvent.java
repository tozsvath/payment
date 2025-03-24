package com.payment.transaction.model.messaging;

import com.payment.transaction.model.PaymentStatus;
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