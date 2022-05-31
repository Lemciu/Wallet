package pl.ml.wallet.transaction.dto;

import java.math.BigDecimal;

public interface TransactionProfitDto {

    Long getId();

    BigDecimal getAmount();

    BigDecimal getBuyPrice();

    BigDecimal getPercentChange();

    BigDecimal getCurrentPrice();

}
