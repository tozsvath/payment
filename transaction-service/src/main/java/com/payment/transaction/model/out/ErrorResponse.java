package com.payment.transaction.model.out;

import java.time.LocalDateTime;
import java.util.UUID;

public record ErrorResponse(
    UUID transactionId,
    String errorCode,
    String message,
    LocalDateTime timestamp
) {

  public static ErrorResponse of(String errorCode, String message) {
    return new ErrorResponse(UUID.randomUUID(), errorCode, message, LocalDateTime.now());
  }
}
