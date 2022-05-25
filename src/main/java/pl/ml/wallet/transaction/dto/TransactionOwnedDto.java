package pl.ml.wallet.transaction.dto;

import java.math.BigDecimal;

public class TransactionOwnedDto {
    private int id;
    private String name;
    private double currentPrice;
    private double allocation;
    private double profit;
    private double percentageProfit;
    private BigDecimal amount;
    private BigDecimal currentValue;

    public TransactionOwnedDto(String name, double currentPrice, double allocation, double profit, double percentageProfit, BigDecimal amount, BigDecimal currentValue) {
        this.name = name;
        this.currentPrice = currentPrice;
        this.allocation = allocation;
        this.profit = profit;
        this.percentageProfit = percentageProfit;
        this.amount = amount;
        this.currentValue = currentValue;
    }

    public TransactionOwnedDto(int id, String name, double currentPrice, double allocation, double profit, double percentageProfit, BigDecimal amount, BigDecimal currentValue) {
        this.id = id;
        this.name = name;
        this.currentPrice = currentPrice;
        this.allocation = allocation;
        this.profit = profit;
        this.percentageProfit = percentageProfit;
        this.amount = amount;
        this.currentValue = currentValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getAllocation() {
        return allocation;
    }

    public void setAllocation(double allocation) {
        this.allocation = allocation;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getPercentageProfit() {
        return percentageProfit;
    }

    public void setPercentageProfit(double percentageProfit) {
        this.percentageProfit = percentageProfit;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }
}
