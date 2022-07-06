package pl.ml.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.ml.wallet.currencyExchange.CurrencyExchange;
import pl.ml.wallet.currencyExchange.CurrencyService;
import pl.ml.wallet.stockTransaction.stock.StockService;

import java.math.BigDecimal;

@SpringBootApplication
public class WalletApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WalletApplication.class, args);
        StockService stockService = context.getBean(StockService.class);
        CurrencyService currencyService = context.getBean(CurrencyService.class);
        CurrencyExchange.setDollarValue(currencyService.getDollarValue());
        stockService.init();
    }

}
