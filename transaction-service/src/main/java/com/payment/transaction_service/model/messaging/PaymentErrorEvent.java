package com.payment.transaction_service.model.messaging;

import com.payment.transaction_service.model.PaymentStatus;
import com.payment.transaction_service.model.in.TransactionRequest;
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
