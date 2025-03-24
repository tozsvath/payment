package com.payment.transaction.model.messaging;

import com.payment.transaction.model.PaymentStatus;
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
