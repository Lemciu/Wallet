package pl.ml.wallet.currencyExchange;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ml.wallet.currencyExchange.api.Dollar;

import java.math.BigDecimal;

@Service
public class CurrencyService {

    private static final String URL = "http://api.nbp.pl/api/exchangerates/rates/a/usd/";

    @Bean
    public BigDecimal getDollarValue() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            Dollar dollar = restTemplate.getForObject(URL, Dollar.class);
            return dollar.getRates().get(0).getMid();
        } catch (Exception e) {
            throw new CurrencyDataNotAvailableException();
        }
    }
}
