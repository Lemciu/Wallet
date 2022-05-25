package pl.ml.wallet.stock.dto;

import java.math.BigDecimal;

public class StockInfoDto {
    private Long marketCapRank;
    private String name;
    private String symbol;
    private BigDecimal currentPrice;
    private BigDecimal marketCap;
    private Double marketCapDominance;
    private BigDecimal fullyDilutedMarketCap;
    private Double percentChange24H;
    private BigDecimal volume24H;
    private Double volumeChange24H;
    private boolean favourite;
    private BigDecimal maxSupply;
    private BigDecimal totalSupply;
    private BigDecimal circulatingSupply;

    public StockInfoDto() {
    }

    public StockInfoDto(Long marketCapRank, String name, String symbol, BigDecimal currentPrice, BigDecimal marketCap, Double marketCapDominance, BigDecimal fullyDilutedMarketCap, Double percentChange24H, BigDecimal volume24H, Double volumeChange24H, boolean favourite, BigDecimal maxSupply, BigDecimal totalSupply, BigDecimal circulatingSupply) {
        this.marketCapRank = marketCapRank;
        this.name = name;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.marketCap = marketCap;
        this.marketCapDominance = marketCapDominance;
        this.fullyDilutedMarketCap = fullyDilutedMarketCap;
        this.percentChange24H = percentChange24H;
        this.volume24H = volume24H;
        this.volumeChange24H = volumeChange24H;
        this.favourite = favourite;
        this.maxSupply = maxSupply;
        this.totalSupply = totalSupply;
        this.circulatingSupply = circulatingSupply;
    }

    public StockInfoDto(String name, String symbol, BigDecimal currentPrice, BigDecimal marketCap, Double marketCapDominance, BigDecimal fullyDilutedMarketCap, Double percentChange24H, BigDecimal volume24H, Double volumeChange24H, boolean favourite, BigDecimal maxSupply, BigDecimal totalSupply, BigDecimal circulatingSupply) {
        this.name = name;
        this.symbol = symbol;
        this.currentPrice = currentPrice;
        this.marketCap = marketCap;
        this.marketCapDominance = marketCapDominance;
        this.fullyDilutedMarketCap = fullyDilutedMarketCap;
        this.percentChange24H = percentChange24H;
        this.volume24H = volume24H;
        this.volumeChange24H = volumeChange24H;
        this.favourite = favourite;
        this.maxSupply = maxSupply;
        this.totalSupply = totalSupply;
        this.circulatingSupply = circulatingSupply;
    }

    public Long getMarketCapRank() {
        return marketCapRank;
    }

    public void setMarketCapRank(Long marketCapRank) {
        this.marketCapRank = marketCapRank;
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

    public Double getMarketCapDominance() {
        return marketCapDominance;
    }

    public void setMarketCapDominance(Double marketCapDominance) {
        this.marketCapDominance = marketCapDominance;
    }

    public BigDecimal getFullyDilutedMarketCap() {
        return fullyDilutedMarketCap;
    }

    public void setFullyDilutedMarketCap(BigDecimal fullyDilutedMarketCap) {
        this.fullyDilutedMarketCap = fullyDilutedMarketCap;
    }

    public Double getPercentChange24H() {
        return percentChange24H;
    }

    public void setPercentChange24H(Double percentChange24H) {
        this.percentChange24H = percentChange24H;
    }

    public BigDecimal getVolume24H() {
        return volume24H;
    }

    public void setVolume24H(BigDecimal volume24H) {
        this.volume24H = volume24H;
    }

    public Double getVolumeChange24H() {
        return volumeChange24H;
    }

    public void setVolumeChange24H(Double volumeChange24H) {
        this.volumeChange24H = volumeChange24H;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public BigDecimal getMaxSupply() {
        return maxSupply;
    }

    public void setMaxSupply(BigDecimal maxSupply) {
        this.maxSupply = maxSupply;
    }

    public BigDecimal getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(BigDecimal totalSupply) {
        this.totalSupply = totalSupply;
    }

    public BigDecimal getCirculatingSupply() {
        return circulatingSupply;
    }

    public void setCirculatingSupply(BigDecimal circulatingSupply) {
        this.circulatingSupply = circulatingSupply;
    }
}
