package com.payment.transaction.model.out;


import java.math.BigDecimal;

public record TransactionResponse(
    String transactionId,
    String status,
    String message,
    BigDecimal balanceAfterTransaction
) {

}
