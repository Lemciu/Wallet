package pl.ml.wallet.stock.dto;

import java.math.BigDecimal;

public class StockMarketDto {
    private long id;
    private String name;
    private String symbol;
    private BigDecimal currentPrice;
    private BigDecimal marketCap;
    private Double percentChange1H;
    private Double percentChange24H;
    private Double percentChange7D;
    private boolean favourite;

    public StockMarketDto() {
    }

    public StockMarketDto(long id, String name, String symbol, BigDecimal currentPrice, BigDecimal marketCap, Double percentChange1H, Double percentChange24H, Double percentChange7D, boolean favourite) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.marketCap = marketCap;
        this.percentChange1H = percentChange1H;
        this.percentChange24H = percentChange24H;
        this.percentChange7D = percentChange7D;
        this.favourite = favourite;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public BigDecimal getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }

    public BigDecimal getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(BigDecimal marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPercentChange1H() {
        return percentChange1H;
    }

    public void setPercentChange1H(Double percentChange1H) {
        this.percentChange1H = percentChange1H;
    }

    public Double getPercentChange24H() {
        return percentChange24H;
    }

    public void setPercentChange24H(Double percentChange24H) {
        this.percentChange24H = percentChange24H;
    }

    public Double getPercentChange7D() {
        return percentChange7D;
    }

    public void setPercentChange7D(Double percentChange7D) {
        this.percentChange7D = percentChange7D;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }
}
