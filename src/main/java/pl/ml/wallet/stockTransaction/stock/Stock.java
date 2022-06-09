package pl.ml.wallet.stockTransaction.stock;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String symbol;
    private BigDecimal currentPrice;
    private BigDecimal marketCap;
    private Double marketCapDominance;
    private BigDecimal fullyDilutedMarketCap;
    private Double percentChange1H;
    private Double percentChange24H;
    private Double percentChange7D;
    private Double percentChange30D;
    private Double percentChange60D;
    private Double percentChange90D;
    private BigDecimal volume24H;
    private Double volumeChange24H;
    private boolean favourite;
    private BigDecimal maxSupply;
    private BigDecimal totalSupply;
    private BigDecimal circulatingSupply;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Double getPercentChange30D() {
        return percentChange30D;
    }

    public void setPercentChange30D(Double percentChange30D) {
        this.percentChange30D = percentChange30D;
    }

    public Double getPercentChange60D() {
        return percentChange60D;
    }

    public void setPercentChange60D(Double percentChange60D) {
        this.percentChange60D = percentChange60D;
    }

    public Double getPercentChange90D() {
        return percentChange90D;
    }

    public void setPercentChange90D(Double percentChange90D) {
        this.percentChange90D = percentChange90D;
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

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", currentPrice=" + currentPrice +
                ", marketCap=" + marketCap +
                ", marketCapDominance=" + marketCapDominance +
                ", fullyDilutedMarketCap=" + fullyDilutedMarketCap +
                ", percentChange1H=" + percentChange1H +
                ", percentChange24H=" + percentChange24H +
                ", percentChange7D=" + percentChange7D +
                ", percentChange30D=" + percentChange30D +
                ", percentChange60D=" + percentChange60D +
                ", percentChange90D=" + percentChange90D +
                ", volume24H=" + volume24H +
                ", volumeChange24H=" + volumeChange24H +
                ", favourite=" + favourite +
                ", maxSupply=" + maxSupply +
                ", totalSupply=" + totalSupply +
                ", circulatingSupply=" + circulatingSupply +
                '}';
    }
}
