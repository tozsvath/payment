package com.payment.transaction.exception;

public class AccountNotFoundException extends RuntimeException {

  public AccountNotFoundException(Long accountNumber) {
    super("Account with account number " + accountNumber + " not found");
  }

}
