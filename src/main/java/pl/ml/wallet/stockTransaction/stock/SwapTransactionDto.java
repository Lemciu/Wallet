package pl.ml.wallet.stockTransaction.stock;

import java.time.LocalDate;

public class SwapTransactionDto {
    private String symbolBoughtStock;
    private Double boughtAmount;
    private String symbolSoldStock;
    private Double soldAmount;
    private LocalDate date;

    public SwapTransactionDto(String symbolBoughtStock, Double boughtAmount, String symbolSoldStock, Double soldAmount, LocalDate date) {
        this.symbolBoughtStock = symbolBoughtStock;
        this.boughtAmount = boughtAmount;
        this.symbolSoldStock = symbolSoldStock;
        this.soldAmount = soldAmount;
        this.date = date;
    }

    public String getSymbolBoughtStock() {
        return symbolBoughtStock;
    }

    public void setSymbolBoughtStock(String symbolBoughtStock) {
        this.symbolBoughtStock = symbolBoughtStock;
    }

    public Double getBoughtAmount() {
        return boughtAmount;
    }

    public void setBoughtAmount(Double boughtAmount) {
        this.boughtAmount = boughtAmount;
    }

    public String getSymbolSoldStock() {
        return symbolSoldStock;
    }

    public void setSymbolSoldStock(String symbolSoldStock) {
        this.symbolSoldStock = symbolSoldStock;
    }

    public Double getSoldAmount() {
        return soldAmount;
    }

    public void setSoldAmount(Double soldAmount) {
        this.soldAmount = soldAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
