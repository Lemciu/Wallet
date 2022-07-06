package pl.ml.wallet.currencyExchange.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

public class Dollar {
    private List<Rate> rates;

    @JsonProperty("rates")
    public List<Rate> getRates() {
        return rates;
    }

    @JsonProperty("rates")
    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }
}
