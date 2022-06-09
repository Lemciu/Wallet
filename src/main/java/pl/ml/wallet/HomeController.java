package pl.ml.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stockTransaction.stock.Stock;
import pl.ml.wallet.stockTransaction.stock.StockService;
import pl.ml.wallet.stockTransaction.StockTransactionService;
import pl.ml.wallet.transaction.BudgetTransactionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

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
    public String home(@RequestParam (required = false) String range,
            Model model) {
        model.addAttribute("balance", stockTransactionService.getTotalBalance());
        model.addAttribute("percentageProfit", stockTransactionService.getPercentageProfit(range));
        model.addAttribute("profit", stockTransactionService.getProfit(range));
        model.addAttribute("favouriteStocks", stockService.findFavouriteStocks());
        return "portfolio";
    }

    @GetMapping("/budget")
    public String budget(Model model) {

        model.addAttribute("transactions", budgetTransactionService.findAll());
        model.addAttribute("balance", budgetTransactionService.getBalance());
//        wallet
//        teraz wyciągnać z tego bilans wydatki odjąć od przychodów i ten bilansik przekazać do buystock
        return "budget";
    }

    @GetMapping("/cash")
    public String cash(Model model) {

        model.addAttribute("transactions", budgetTransactionService.findAll());
        model.addAttribute("balance", budgetTransactionService.getBalance());
//        wallet
//        teraz wyciągnać z tego bilans wydatki odjąć od przychodów i ten bilansik przekazać do buystock
        return "cash";
    }

    @GetMapping("/swap")
    public String swapCrypto() {
        return "swap";
    }

    @GetMapping("/buyCrypto")
    public String buyCrypto(@RequestParam String symbol,
                            Model model) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        model.addAttribute("symbol", stock.getSymbol());
        model.addAttribute("currentStock", stock.getName());
        model.addAttribute("stocks",  stockService.findAllStockNames());
        model.addAttribute("form", "Buy");
        model.addAttribute("action", "buyCrypto?symbol=");
        BigDecimal balance = budgetTransactionService.getBalance();
        model.addAttribute("balance", balance);
        model.addAttribute("dollarBalance", budgetTransactionService.getBalance().divide(BigDecimal.valueOf(4.28), RoundingMode.HALF_UP));
        model.addAttribute("maxValue", budgetTransactionService.getMaxAmount(balance, stock));
        return "formCrypto";
    }

    @GetMapping("/StocksForm")
    public String stocksToBuy(Model model) {
        System.out.println("gites stocks form");
        model.addAttribute("stocks",  stockService.findAllStockNames());
        return "stockList";
    }

    @PostMapping("/buyStock")
    public String buyBtc(@RequestParam(required = false) String symbol,
                         @RequestParam(required = false) Double amount) {
        stockTransactionService.buyCrypto(amount, symbol);
        return "redirect:/";
    }

    @GetMapping("/sellCrypto")
    public String sellCrypto(@RequestParam String symbol, Model model) {
        model.addAttribute("form", "Sell");
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        model.addAttribute("symbol", stock.getSymbol());
        model.addAttribute("currentStock", stock.getName());
        model.addAttribute("stocks",  stockTransactionService.findAllOwnedStockNames());
        model.addAttribute("form", "Sell");
        model.addAttribute("action", "sellCrypto?symbol=");
        BigDecimal balance = budgetTransactionService.getBalance();
        model.addAttribute("balance", balance);
        model.addAttribute("dollarBalance", budgetTransactionService.getBalance().divide(BigDecimal.valueOf(4.28), RoundingMode.HALF_UP));
        model.addAttribute("maxValue", stockTransactionService.getAmount(symbol));
        return "formCrypto";
    }

}
