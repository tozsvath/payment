package com.payment.transaction.exception;

public class InsufficientFundsException extends RuntimeException {

  public InsufficientFundsException() {
    super("Insufficient funds");
  }

}
