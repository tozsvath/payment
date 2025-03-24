package com.payment.transaction.service;

import com.payment.transaction.exception.AccountNotFoundException;
import com.payment.transaction.exception.InsufficientFundsException;
import com.payment.transaction.messaging.PaymentEventProducer;
import com.payment.transaction.model.EntryType;
import com.payment.transaction.model.PaymentStatus;
import com.payment.transaction.model.dto.Account;
import com.payment.transaction.model.dto.LedgerEntry;
import com.payment.transaction.model.in.TransactionRequest;
import com.payment.transaction.model.messaging.PaymentEvent;
import com.payment.transaction.repository.AccountRepository;
import com.payment.transaction.repository.LedgerRepository;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
@Slf4j
public class TransactionService {

  private final AccountRepository accountRepository;
  private final LedgerRepository ledgerRepository;
  private final PaymentEventProducer paymentEventProducer;
  private final Clock clock;

  @Transactional
  public UUID transfer(TransactionRequest tr) {
    UUID transactionId = UUID.randomUUID();
    log.debug("Processing transaction: {}", transactionId);

    Account senderAccount = getAccountOrThrow(tr.senderAccount());
    Account receiverAccount = getAccountOrThrow(tr.recipientAccount());

    BigDecimal senderBalance = ledgerRepository.getBalance(tr.senderAccount());

    validateFounds(tr, senderBalance);

    LedgerEntry senderEntry = createLedgerEntry(transactionId,
        senderAccount,
        tr,
        EntryType.DEBIT);

    LedgerEntry receiverEntry = createLedgerEntry(
        transactionId,
        receiverAccount,
        tr,
        EntryType.CREDIT);

    accountRepository.save(senderAccount);
    accountRepository.save(receiverAccount);
    ledgerRepository.save(senderEntry);
    ledgerRepository.save(receiverEntry);

    paymentEventProducer.sendPaymentEvent(createPaymentEvent(tr, transactionId, senderEntry,
        senderBalance, receiverEntry));

    return transactionId;
  }

  private Account getAccountOrThrow(Long accountNumber) {
    return accountRepository.findById(accountNumber)
        .orElseThrow(() -> new AccountNotFoundException(accountNumber));
  }

  private void validateFounds(TransactionRequest tr, BigDecimal senderBalance) {
    if (isInsufficientBalance(tr, senderBalance)) {
      throw new InsufficientFundsException();
    }
  }

  private boolean isInsufficientBalance(TransactionRequest tr, BigDecimal senderBalance) {
    return senderBalance.compareTo(tr.amount()) < 0;
  }

  private PaymentEvent createPaymentEvent(TransactionRequest tr, UUID transactionId,
      LedgerEntry senderEntry, BigDecimal senderBalance, LedgerEntry receiverEntry) {
    return new PaymentEvent(
        transactionId,
        senderEntry.getAccountNumber(),
        calculateBalance(tr, senderBalance),
        receiverEntry.getAccountNumber(),
        LocalDateTime.now(clock),
        PaymentStatus.SUCCESS,
        transactionId
    );
  }

  private BigDecimal calculateBalance(TransactionRequest tr, BigDecimal senderBalance) {
    return senderBalance.subtract(tr.amount());
  }

  private LedgerEntry createLedgerEntry(UUID transactionId, Account senderAccount,
      TransactionRequest tr, EntryType debit) {
    return new LedgerEntry(
        transactionId,
        senderAccount.getAccountNumber(),
        tr.amount(),
        debit,
        LocalDateTime.now(clock)
    );
  }
}
