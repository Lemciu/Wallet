package pl.ml.wallet.stock.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Quote {
    private Usd usd;

    @JsonProperty("USD")
    public Usd getUsd() { return usd; }
    @JsonProperty("USD")
    public void setUsd(Usd value) { this.usd = value; }
}
