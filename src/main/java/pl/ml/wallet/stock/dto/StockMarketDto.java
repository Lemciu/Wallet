package pl.ml.wallet.stock.dto;

import java.math.BigDecimal;

public interface StockMarketDto {

    Long getId();

    String getName();

    String getSymbol();

    BigDecimal getCurrentPrice();

    BigDecimal getMarketCap();

    Double getPercentChange();

    Boolean getFavourite();

}
