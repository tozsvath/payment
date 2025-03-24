package com.payment.transaction.controller;

import com.payment.transaction.model.in.TransactionRequest;
import com.payment.transaction.service.TransactionService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@AllArgsConstructor
@Slf4j
public class TransactionController {

  private final TransactionService transactionService;

  @PostMapping
  @Retryable(
      value = OptimisticLockingFailureException.class,
      maxAttempts = 3,
      backoff = @Backoff(delay = 1000)
  )
  public ResponseEntity<String> createTransaction(
      @Valid @RequestBody TransactionRequest request) {
    log.info("Started processing transaction");
    UUID transactionId = transactionService.transfer(request);
    return ResponseEntity.ok(transactionId.toString());
  }

  @GetMapping("/{id}")
  public ResponseEntity<String> getTransaction(@PathVariable UUID id) {
    return ResponseEntity.ok("Transaction with id " + id + " is being processed!");
  }
}
