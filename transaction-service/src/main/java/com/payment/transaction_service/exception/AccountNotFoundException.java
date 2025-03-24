package com.payment.transaction_service.exception;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(Long accountNumber) {
    super("Account with account number " + accountNumber + " not found");
  }

}
