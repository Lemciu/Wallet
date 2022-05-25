package pl.ml.wallet.stock.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Usd {
    private BigDecimal price;
    private BigDecimal volume24H;
    private double volumeChange24H;
    private double percentChange1H;
    private double percentChange24H;
    private double percentChange7D;
    private double percentChange30D;
    private double percentChange60D;
    private double percentChange90D;
    private BigDecimal marketCap;
    private double marketCapDominance;
    private BigDecimal fullyDilutedMarketCap;

    @JsonProperty("price")
    public BigDecimal getPrice() { return price; }
    @JsonProperty("price")
    public void setPrice(BigDecimal value) { this.price = value; }

    @JsonProperty("volume_24h")
    public BigDecimal getVolume24H() { return volume24H; }
    @JsonProperty("volume_24h")
    public void setVolume24H(BigDecimal value) { this.volume24H = value; }

    @JsonProperty("volume_change_24h")
    public double getVolumeChange24H() { return volumeChange24H; }
    @JsonProperty("volume_change_24h")
    public void setVolumeChange24H(double value) { this.volumeChange24H = value; }

    @JsonProperty("percent_change_1h")
    public double getPercentChange1H() { return percentChange1H; }
    @JsonProperty("percent_change_1h")
    public void setPercentChange1H(double value) { this.percentChange1H = value; }

    @JsonProperty("percent_change_24h")
    public double getPercentChange24H() { return percentChange24H; }
    @JsonProperty("percent_change_24h")
    public void setPercentChange24H(double value) { this.percentChange24H = value; }

    @JsonProperty("percent_change_7d")
    public double getPercentChange7D() { return percentChange7D; }
    @JsonProperty("percent_change_7d")
    public void setPercentChange7D(double value) { this.percentChange7D = value; }

    @JsonProperty("percent_change_30d")
    public double getPercentChange30D() { return percentChange30D; }
    @JsonProperty("percent_change_30d")
    public void setPercentChange30D(double value) { this.percentChange30D = value; }

    @JsonProperty("percent_change_60d")
    public double getPercentChange60D() { return percentChange60D; }
    @JsonProperty("percent_change_60d")
    public void setPercentChange60D(double value) { this.percentChange60D = value; }

    @JsonProperty("percent_change_90d")
    public double getPercentChange90D() { return percentChange90D; }
    @JsonProperty("percent_change_90d")
    public void setPercentChange90D(double value) { this.percentChange90D = value; }

    @JsonProperty("market_cap")
    public BigDecimal getMarketCap() { return marketCap; }
    @JsonProperty("market_cap")
    public void setMarketCap(BigDecimal value) { this.marketCap = value; }

    @JsonProperty("market_cap_dominance")
    public double getMarketCapDominance() { return marketCapDominance; }
    @JsonProperty("market_cap_dominance")
    public void setMarketCapDominance(double value) { this.marketCapDominance = value; }

    @JsonProperty("fully_diluted_market_cap")
    public BigDecimal getFullyDilutedMarketCap() { return fullyDilutedMarketCap; }
    @JsonProperty("fully_diluted_market_cap")
    public void setFullyDilutedMarketCap(BigDecimal value) { this.fullyDilutedMarketCap = value; }

}
