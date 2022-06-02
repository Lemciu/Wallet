package pl.ml.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stock.StockService;
import pl.ml.wallet.transaction.TransactionService;

@Controller
public class HomeController {
    private StockService stockService;
    private TransactionService transactionService;

    public HomeController(StockService stockService, TransactionService transactionService) {
        this.stockService = stockService;
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String home(@RequestParam (required = false) String range,
            Model model) {
        model.addAttribute("balance", transactionService.getTotalBalance());
        model.addAttribute("percentageProfit", transactionService.getPercentageProfit(range));
        model.addAttribute("profit", transactionService.getProfit(range));
        model.addAttribute("favouriteStocks", stockService.findFavouriteStocks());
        return "portfolio";
    }

    @GetMapping("/savings")
    public String savings() {
        return "savings";
    }

    @GetMapping("/swap")
    public String swapCrypto() {
        return "swap";
    }

    @GetMapping("/buyCrypto")
    public String buyCrypto(Model model) {
        model.addAttribute("form", "Buy");
        return "formCrypto";
    }

    @GetMapping("/sellCrypto")
    public String sellCrypto(Model model) {
        model.addAttribute("form", "Sell");
        return "formCrypto";
    }

}
