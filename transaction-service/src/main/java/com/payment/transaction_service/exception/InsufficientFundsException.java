package com.payment.transaction_service.exception;

public class InsufficientFundsException extends RuntimeException {

  public InsufficientFundsException() {
    super("Insufficient funds");
  }

}
