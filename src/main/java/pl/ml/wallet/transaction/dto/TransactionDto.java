package pl.ml.wallet.transaction.dto;

import java.math.BigDecimal;

public interface TransactionDto {
    BigDecimal getAmount();

    BigDecimal getCurrentPrice();

    BigDecimal getBuyPrice();
}
