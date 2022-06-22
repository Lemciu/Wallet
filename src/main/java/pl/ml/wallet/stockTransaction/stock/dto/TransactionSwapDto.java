package pl.ml.wallet.stockTransaction.stock.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionSwapDto {
    private String stockSold;
    private BigDecimal stockSoldAmount;
    private String stockBought;
    private BigDecimal stockBoughtAmount;
    private LocalDate date;

    public TransactionSwapDto(String stockSold, BigDecimal stockSoldAmount, String stockBought, BigDecimal stockBoughtAmount, LocalDate date) {
        this.stockSold = stockSold;
        this.stockSoldAmount = stockSoldAmount;
        this.stockBought = stockBought;
        this.stockBoughtAmount = stockBoughtAmount;
        this.date = date;
    }

    public String getStockSold() {
        return stockSold;
    }

    public void setStockSold(String stockSold) {
        this.stockSold = stockSold;
    }

    public BigDecimal getStockSoldAmount() {
        return stockSoldAmount;
    }

    public void setStockSoldAmount(BigDecimal stockSoldAmount) {
        this.stockSoldAmount = stockSoldAmount;
    }

    public String getStockBought() {
        return stockBought;
    }

    public void setStockBought(String stockBought) {
        this.stockBought = stockBought;
    }

    public BigDecimal getStockBoughtAmount() {
        return stockBoughtAmount;
    }

    public void setStockBoughtAmount(BigDecimal stockBoughtAmount) {
        this.stockBoughtAmount = stockBoughtAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
