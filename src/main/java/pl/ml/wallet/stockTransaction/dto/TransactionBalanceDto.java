package pl.ml.wallet.stockTransaction.dto;

import java.math.BigDecimal;

public interface TransactionBalanceDto {
    BigDecimal getAmount(); // może Double?

    BigDecimal getCurrentPrice();
}
