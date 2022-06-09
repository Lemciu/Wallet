package pl.ml.wallet.stockTransaction.stock.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class StockDto {
    private long id;
    private String name;
    private String symbol;
    private Quote quote;
    private BigDecimal maxSupply;
    private BigDecimal totalSupply;
    private BigDecimal circulatingSupply;

    @JsonProperty("circulating_supply")
    public BigDecimal getCirculatingSupply() { return circulatingSupply; }
    @JsonProperty("circulating_supply")
    public void setCirculationSupply(BigDecimal value) { this.circulatingSupply = value; }

    @JsonProperty("max_supply")
    public BigDecimal getMaxSupply() { return maxSupply; }
    @JsonProperty("max_supply")
    public void setMaxSupply(BigDecimal value) { this.maxSupply = value; }

    @JsonProperty("total_supply")
    public BigDecimal getTotalSupply() { return totalSupply; }
    @JsonProperty("total_supply")
    public void setTotalSupply(BigDecimal value) { this.totalSupply = value; }


    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("symbol")
    public String getSymbol() { return symbol; }
    @JsonProperty("symbol")
    public void setSymbol(String value) { this.symbol = value; }

    @JsonProperty("quote")
    public Quote getQuote() { return quote; }
    @JsonProperty("quote")
    public void setQuote(Quote value) { this.quote = value; }
}
