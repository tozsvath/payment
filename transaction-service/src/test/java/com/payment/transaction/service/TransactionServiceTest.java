package com.payment.transaction.service;

import static com.payment.transaction.model.Constants.INSUFFICIENT_FUNDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.payment.transaction.exception.InsufficientFundsException;
import com.payment.transaction.messaging.PaymentEventProducer;
import com.payment.transaction.model.dto.Account;
import com.payment.transaction.model.dto.LedgerEntry;
import com.payment.transaction.model.in.TransactionRequest;
import com.payment.transaction.repository.AccountRepository;
import com.payment.transaction.repository.LedgerRepository;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.EnableRetry;

@ExtendWith(MockitoExtension.class)
@EnableRetry
class TransactionServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private LedgerRepository ledgerRepository;

  @Mock
  private PaymentEventProducer paymentEventProducer;

  private Clock clock = Clock.fixed(Instant.now(), ZoneId.of("UTC"));

  private Account senderAccount;
  private Account receiverAccount;
  private TransactionRequest transactionRequest;
  private TransactionService transactionService;

  @BeforeEach
  void setUp() {
    transactionService = new TransactionService(accountRepository, ledgerRepository,
        paymentEventProducer, clock);
    senderAccount = new Account(1L, 123456L);
    receiverAccount = new Account(2L, 654321L);
    transactionRequest = new TransactionRequest(1L, 2L, new BigDecimal("100.00"));
  }

  @Test
  void transfer_SuccessfulTransaction() {
    when(accountRepository.findById(transactionRequest.senderAccount()))
        .thenReturn(Optional.of(senderAccount));
    when(accountRepository.findById(transactionRequest.recipientAccount()))
        .thenReturn(Optional.of(receiverAccount));
    when(ledgerRepository.getBalance(transactionRequest.senderAccount()))
        .thenReturn(new BigDecimal("500.00"));

    UUID transactionId = transactionService.transfer(transactionRequest);

    assertNotNull(transactionId);
    assertEquals(36, transactionId.toString().length());
    verify(ledgerRepository, times(2)).save(any(LedgerEntry.class));
    verify(paymentEventProducer, times(1)).sendPaymentEvent(any());

  }

  @Test
  void transfer_ShouldThrowInsufficientFundsException_WhenSenderHasInsufficientFunds() {
    when(accountRepository.findById(senderAccount.getAccountNumber())).thenReturn(
        Optional.of(senderAccount));
    when(accountRepository.findById(receiverAccount.getAccountNumber())).thenReturn(
        Optional.of(receiverAccount));
    when(ledgerRepository.getBalance(senderAccount.getAccountNumber())).thenReturn(
        new BigDecimal("50.00"));

    InsufficientFundsException exception = assertThrows(
        InsufficientFundsException.class,
        () -> transactionService.transfer(transactionRequest)
    );

    assertEquals(INSUFFICIENT_FUNDS, exception.getMessage());
  }

  @Test
  void transfer_SenderAccountNotFound() {
    when(accountRepository.findById(transactionRequest.senderAccount()))
        .thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class,
        () -> transactionService.transfer(transactionRequest));

    assertEquals("Account with account number 1 not found", exception.getMessage());
  }

  @Test
  void transfer_ReceiverAccountNotFound() {
    when(accountRepository.findById(transactionRequest.senderAccount()))
        .thenReturn(Optional.of(senderAccount));
    when(accountRepository.findById(transactionRequest.recipientAccount()))
        .thenReturn(Optional.empty());

    RuntimeException exception = assertThrows(RuntimeException.class,
        () -> transactionService.transfer(transactionRequest));

    assertEquals("Account with account number 2 not found", exception.getMessage());
  }

}
