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
    public String accounts(@RequestParam(required = false) String range,
                           Model model) {
        model.addAttribute("accounts",
                stockTransactionService.toAccountDto(stockTransactionService.findAllOwnedAccounts(range)));
        return "accounts";
    }

    @GetMapping("/accountStock")
    public String accountStock(@RequestParam String symbol,
                               @RequestParam(required = false) String range,
                               @RequestParam(required = false) String type,
                               @RequestParam(required = false) String secondType,
                               Model model) {
        model.addAttribute("range", stockService.getRange(range));
        if (secondType == null) {
            secondType = "All";
        }
        if (type == null) {
            type = "All";
        }

        model.addAttribute("secondType", secondType);
        model.addAttribute("type", type);
        model.addAttribute("stock", stockTransactionService.findAllTransactionToProfile(symbol, range));
        model.addAttribute("transactions",
                stockTransactionService.findAllTransactionsByStock(stockService.findBySymbol(symbol).get().getId(), type, secondType));

        return "accountStock";
    }
}
