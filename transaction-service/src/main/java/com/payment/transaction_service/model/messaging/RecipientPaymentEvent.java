package com.payment.transaction_service.model.messaging;

import com.payment.transaction_service.model.PaymentStatus;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecipientPaymentEvent(
    String transactionId,
    String recipientAccountNumber,
    BigDecimal senderBalance,
    LocalDateTime timestamp,
    PaymentStatus status
) {

}
