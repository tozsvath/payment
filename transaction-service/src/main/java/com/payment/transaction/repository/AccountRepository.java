package com.payment.transaction.repository;

import com.payment.transaction.model.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
