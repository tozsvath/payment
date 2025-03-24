package com.payment.transaction_service.model.in;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record TransactionRequest(
    @NotNull Long senderAccount,
    @NotNull Long recipientAccount,
    @NotNull BigDecimal amount
) {

}

