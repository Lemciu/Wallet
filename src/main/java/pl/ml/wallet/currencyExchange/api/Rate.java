package pl.ml.wallet.currencyExchange.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class Rate {
    private BigDecimal mid;

    @JsonProperty("mid")
    public BigDecimal getMid() {
        return mid;
    }

    @JsonProperty("mid")
    public void setMid(BigDecimal mid) {
        this.mid = mid;
    }
}
