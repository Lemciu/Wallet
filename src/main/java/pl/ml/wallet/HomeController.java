package pl.ml.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stockTransaction.stock.Stock;
import pl.ml.wallet.stockTransaction.stock.StockService;
import pl.ml.wallet.stockTransaction.StockTransactionService;
import pl.ml.wallet.stockTransaction.stock.SwapTransactionDto;
import pl.ml.wallet.transaction.BudgetTransactionService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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
//        model.addAttribute("balance", budgetTransactionService.getBalance());
        model.addAttribute("balance", stockTransactionService.getTotalBalanceA());
        model.addAttribute("cash", budgetTransactionService.getBalanceInPln());
        model.addAttribute("cashPercent", stockTransactionService.getBalanceInPercent());
        model.addAttribute("savings", budgetTransactionService.getSavingsAmount());
        model.addAttribute("savingsPercent", stockTransactionService.getSavingsInPercent());
        model.addAttribute("investments", stockTransactionService.getTotalBalance().multiply(BigDecimal.valueOf(4.28)));
        model.addAttribute("investmentsInDollar", stockTransactionService.getTotalBalance());
        model.addAttribute("investmentsProfit", 4.21);
        model.addAttribute("investmentsPercent", stockTransactionService.getInvestmentsInPercent());

        return "budget";
    }

    @GetMapping("/cash")
    public String cash(Model model) {

        model.addAttribute("transactions", budgetTransactionService.findAll());
        model.addAttribute("balance", budgetTransactionService.getBalanceInPln());
//        wallet
//        teraz wyciągnać z tego bilans wydatki odjąć od przychodów i ten bilansik przekazać do buystock
        return "cash";
    }

    @PostMapping("/swapCrypto")
    public String swapCryptoTo(@RequestParam String from,
                               @RequestParam String to,
                               @RequestParam Double amount,
                               @RequestParam Double rate,
                               Model model) {
        stockTransactionService.swapCrypto(from, amount, to, rate);
        return "redirect:/swapHistory";
    }

    @GetMapping("/swapHistory")
    public String swapHistory(Model model) {
        model.addAttribute("transactions", stockTransactionService.findAllSwapTransactions());
//        0.1 BTC -> 0.78 ETH, 10.06.2022
        return "swapHistory";
    }

    @GetMapping("/swap")
    public String swapCrypto(@RequestParam String from,
                             @RequestParam String to,
                             Model model) {

        model.addAttribute("stockAmount", stockTransactionService.getAmount(from));
        Stock sellStock = stockService.findBySymbol(from).orElseThrow();
        Stock buyStock = stockService.findBySymbol(to).orElseThrow();
        // to zamienić na DTO!
//        czy ja tutaj powinienem uzywać tego Stock? Raczej jakiegoś Dto
//        nazwa,symbol, amount
        model.addAttribute("sellStock", sellStock);
        model.addAttribute("buyStock", buyStock);
        model.addAttribute("rate", stockTransactionService.getRatio(sellStock, buyStock));
        model.addAttribute("symbol", sellStock.getSymbol());
        model.addAttribute("stocks",
                stockTransactionService.toAccountDto(stockTransactionService.findAllOwnedAccounts(null)));
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
//        model.addAttribute("action", "buyCrypto?symbol=");
        model.addAttribute("action", "buyCrypto");
        BigDecimal balance = budgetTransactionService.getBalanceInPln();
        model.addAttribute("balance", balance);
        model.addAttribute("dollarBalance", budgetTransactionService.getBalanceInPln().divide(BigDecimal.valueOf(4.28), RoundingMode.HALF_UP));
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
                         @RequestParam String transactionType,
                         @RequestParam(required = false) Double amount) {
        switch (transactionType) {
            case "buyCrypto":
                stockTransactionService.buyCrypto(amount, symbol);
                break;
            case "sellCrypto":
                stockTransactionService.sellCrypto(amount, symbol);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + transactionType);
        }
        return "redirect:/" + transactionType + "?symbol=" + symbol;
    }

    @GetMapping("/sellCrypto")
    public String sellCrypto(@RequestParam String symbol, Model model) {
        model.addAttribute("form", "Sell");
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        model.addAttribute("symbol", stock.getSymbol());
        model.addAttribute("currentStock", stock.getName());
        model.addAttribute("stocks",  stockTransactionService.findAllOwnedStockNames());
        model.addAttribute("form", "Sell");
//        model.addAttribute("action", "sellCrypto?symbol=");
        model.addAttribute("action", "sellCrypto");
        BigDecimal balance = budgetTransactionService.getBalanceInPln();
        model.addAttribute("balance", balance);
        model.addAttribute("dollarBalance", budgetTransactionService.getBalanceInPln().divide(BigDecimal.valueOf(4.28), RoundingMode.HALF_UP));
        model.addAttribute("maxValue", stockTransactionService.getAmount(symbol));
        return "formCrypto";
    }

}
