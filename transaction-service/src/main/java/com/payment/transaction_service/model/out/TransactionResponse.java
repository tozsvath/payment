package com.payment.transaction_service.model.out;


import java.math.BigDecimal;

public record TransactionResponse(
    String transactionId,
    String status,
    String message,
    BigDecimal balanceAfterTransaction
) {

}
