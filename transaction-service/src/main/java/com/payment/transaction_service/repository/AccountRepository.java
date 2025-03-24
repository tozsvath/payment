package com.payment.transaction_service.repository;

import com.payment.transaction_service.model.dto.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
