package pl.ml.wallet.transaction.dto;

import pl.ml.wallet.stock.Stock;

import java.math.BigDecimal;

public class TransactionInvestDto {
    private Stock stock;
    private double amount;
    private BigDecimal currentValue;

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }
}
