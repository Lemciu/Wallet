package pl.ml.wallet.transaction.dto;

import java.math.BigDecimal;

public interface TransactionBalanceDto {
    BigDecimal getAmount(); // może Double?

    BigDecimal getCurrentPrice();
}
