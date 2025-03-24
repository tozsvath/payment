package com.payment.transaction.repository;

import com.payment.transaction.model.dto.LedgerEntry;
import java.math.BigDecimal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LedgerRepository extends JpaRepository<LedgerEntry, Long> {

  @Query("SELECT SUM(CASE WHEN e.type = 'CREDIT' THEN e.amount ELSE -e.amount END) " +
      "FROM LedgerEntry e WHERE e.accountNumber = :accountNumber")
  BigDecimal getBalance(@Param("accountNumber") Long accountNumber);

}
