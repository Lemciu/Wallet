package pl.ml.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.ml.wallet.stockTransaction.stock.StockService;
import pl.ml.wallet.stockTransaction.StockTransactionService;
import pl.ml.wallet.transaction.BudgetTransactionService;

@Controller
public class HomeController {
    private StockService stockService;
    private StockTransactionService stockTransactionService;
    private BudgetTransactionService budgetTransactionService;

    public HomeController(StockService stockService, StockTransactionService stockTransactionService, BudgetTransactionService budgetTransactionService) {
        this.stockService = stockService;
        this.stockTransactionService = stockTransactionService;
        this.budgetTransactionService = budgetTransactionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("balance", stockTransactionService.getTotalBalanceInPln());
        model.addAttribute("cash", budgetTransactionService.getCashBalance());
        model.addAttribute("cashPercent", stockTransactionService.getBalanceInPercent());
        model.addAttribute("investmentsInPln", CurrencyExchange.toPln(stockTransactionService.getCryptoBalance()));
        model.addAttribute("investments", stockTransactionService.getCryptoBalance());
        model.addAttribute("investmentsPercent", stockTransactionService.getInvestmentsInPercent());
        model.addAttribute("favouriteStocks", stockService.findFavouriteStocks());
        return "dashboard";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

}
