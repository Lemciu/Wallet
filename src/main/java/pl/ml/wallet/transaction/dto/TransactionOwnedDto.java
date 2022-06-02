package pl.ml.wallet.transaction.dto;

import java.math.BigDecimal;

public interface TransactionOwnedDto {

    String getName();

    String getSymbol();

    BigDecimal getAmount();

    BigDecimal getCurrentPrice();

    Double getPercentChange();

}
