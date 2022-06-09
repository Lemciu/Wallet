package pl.ml.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import pl.ml.wallet.stockTransaction.stock.StockService;

@SpringBootApplication
public class WalletApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(WalletApplication.class, args);
        StockService stockService = context.getBean(StockService.class);
        stockService.init();
    }

}
