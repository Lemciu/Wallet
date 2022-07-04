package pl.ml.wallet.stockTransaction.dto;

import java.math.BigDecimal;

public class AccountProfileDto {
    private String name;
    private String symbol;
    private BigDecimal amount;
    private BigDecimal value;
    private Double percentChange;
    private BigDecimal valueChange;
    private boolean favourite;
    private BigDecimal currentPrice;

    public AccountProfileDto(String name, String symbol, BigDecimal amount, BigDecimal value, Double percentChange, BigDecimal valueChange, boolean favourite, BigDecimal currentPrice) {
        this.name = name;
        this.symbol = symbol;
        this.amount = amount;
        this.value = value;
        this.percentChange = percentChange;
        this.valueChange = valueChange;
        this.favourite = favourite;
        this.currentPrice = currentPrice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public BigDecimal getValueChange() {
        return valueChange;
    }

    public void setValueChange(BigDecimal valueChange) {
        this.valueChange = valueChange;
    }

    public Double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(Double percentChange) {
        this.percentChange = percentChange;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

}
