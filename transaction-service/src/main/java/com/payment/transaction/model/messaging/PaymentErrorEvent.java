package com.payment.transaction.model.messaging;

import com.payment.transaction.model.PaymentStatus;
import com.payment.transaction.model.in.TransactionRequest;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentErrorEvent(
    TransactionRequest originalRequest,
    UUID transactionId,
    Long senderAccountNumber,
    String errorMessage,
    LocalDateTime timestamp,
    PaymentStatus status
) {

}
