package pl.ml.wallet.stockTransaction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stockTransaction.stock.StockService;

@Controller
public class StockTransactionController {
    private StockTransactionService stockTransactionService;
    private StockService stockService;

    public StockTransactionController(StockTransactionService stockTransactionService, StockService stockService) {
        this.stockTransactionService = stockTransactionService;
        this.stockService = stockService;
    }

    @GetMapping("/accounts")
    public String accounts(@RequestParam (required = false) String range,
            Model model) {
        model.addAttribute("accounts",
                stockTransactionService.toAccountDto(stockTransactionService.findAllOwnedAccounts(range)));
        return "accounts";
    }

    @GetMapping("/accountStock")
    public String accountStock(@RequestParam String symbol,
                               @RequestParam (required = false) String range,
                                Model model) {
        model.addAttribute("stock", stockTransactionService.findAllTransactionToProfile(symbol, range));
        return "accountStock";
    }
}
