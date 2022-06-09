package pl.ml.wallet.stockTransaction.dto;

import java.math.BigDecimal;

public interface TransactionOwnedDto {

    String getName();

    String getSymbol();

    BigDecimal getAmount();

    BigDecimal getCurrentPrice();

    Double getPercentChange();

    BigDecimal getBuyPrice();

    Boolean getFavourite();

}
