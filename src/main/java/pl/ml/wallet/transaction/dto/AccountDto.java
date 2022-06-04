package pl.ml.wallet.transaction.dto;

import java.math.BigDecimal;

public class AccountDto {

    private String name;

    private String symbol;

    private BigDecimal amount;

    private BigDecimal value;

    private Double percentChange;

    public AccountDto(String name, String symbol, BigDecimal amount, BigDecimal value, Double percentChange) {
        this.name = name;
        this.symbol = symbol;
        this.amount = amount;
        this.value = value;
        this.percentChange = percentChange;
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

    public Double getPercentChange() {
        return percentChange;
    }

    public void setPercentChange(Double percentChange) {
        this.percentChange = percentChange;
    }
}
