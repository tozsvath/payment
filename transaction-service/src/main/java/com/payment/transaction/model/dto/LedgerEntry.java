package com.payment.transaction.model.dto;

import com.payment.transaction.model.EntryType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "ledger")
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class LedgerEntry {

  @Id
  @Column(columnDefinition = "UUID", updatable = false, nullable = false)
  private final UUID id = UUID.randomUUID();

  private final UUID transactionId;

  private final Long accountNumber;

  @Column(nullable = false)
  private final BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private final EntryType type;

  @CreationTimestamp
  @Column(name = "created_at", updatable = false, nullable = false)
  private LocalDateTime createdAt;

}
