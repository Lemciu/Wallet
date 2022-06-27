package pl.ml.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stockTransaction.stock.Stock;
import pl.ml.wallet.stockTransaction.stock.StockService;
import pl.ml.wallet.stockTransaction.StockTransactionService;
import pl.ml.wallet.transaction.BudgetTransaction;
import pl.ml.wallet.transaction.BudgetTransactionService;
import pl.ml.wallet.transaction.TransactionType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

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

        model.addAttribute("transactions", budgetTransactionService.findAll());
        model.addAttribute("balance", stockTransactionService.getTotalBalance());
        model.addAttribute("cash", budgetTransactionService.getBalanceInPln());
        model.addAttribute("cashPercent", stockTransactionService.getBalanceInPercent());
        model.addAttribute("savings", budgetTransactionService.getSavingsAmount());
        model.addAttribute("savingsPercent", stockTransactionService.getSavingsInPercent());
        model.addAttribute("investmentsInPln", stockTransactionService.getCryptoBalance().multiply(BigDecimal.valueOf(4.28)));
        model.addAttribute("investments", stockTransactionService.getCryptoBalance());
        model.addAttribute("investmentsProfit", 4.21);
        model.addAttribute("investmentsPercent", stockTransactionService.getInvestmentsInPercent());

        model.addAttribute("balance", stockTransactionService.getTotalBalance());
        model.addAttribute("percentageProfit", stockTransactionService.getPercentageProfit(range));
        model.addAttribute("profit", stockTransactionService.getProfit(range));
        model.addAttribute("favouriteStocks", stockService.findFavouriteStocks());
        return "dashboard";
    }

    @GetMapping("/crypto")
    public String budget(@RequestParam (required = false) String range,
                         Model model) {
        model.addAttribute("balance", stockTransactionService.getTotalBalance());
        model.addAttribute("percentageProfit", stockTransactionService.getPercentageProfit(range));
        model.addAttribute("profit", stockTransactionService.getProfit(range));
        model.addAttribute("favouriteStocks", stockService.findFavouriteStocks());

        return "budget";
    }

    @GetMapping("/cash")
    public String cash(Model model) {
        model.addAttribute("transactions", budgetTransactionService.findAll());
        model.addAttribute("balance", budgetTransactionService.getBalanceInPln());
        return "cash";
    }

    @GetMapping("/cashWallet")
    public String cash1(Model model) {
        model.addAttribute("transactions", budgetTransactionService.findAll());
        model.addAttribute("balance", budgetTransactionService.getBalanceInPln());
        return "cash";
    }

    @GetMapping("/cryptoWallet")
    public String cash31(@RequestParam (required = false) String range,
                         Model model) {
        model.addAttribute("accounts",
                stockTransactionService.toAccountDto(stockTransactionService.findAllOwnedAccounts(range)));
        model.addAttribute("balance", stockTransactionService.getCryptoBalance());
        model.addAttribute("percentageProfit", stockTransactionService.getPercentageProfit(range));
        model.addAttribute("profit", stockTransactionService.getProfit(range));
        model.addAttribute("favouriteStocks", stockService.findFavouriteStocks());
        return "cryptoWallet";
    }

    @PostMapping("/swapCrypto")
    public String swapCryptoTo(@RequestParam String from,
                               @RequestParam String to,
                               @RequestParam Double amount,
                               @RequestParam Double rate) {
        stockTransactionService.swapCrypto(from, amount, to, rate);
        return "redirect:/swapHistory";
    }

    @GetMapping("/swapHistory")
    public String swapHistory(Model model) {
        model.addAttribute("transactions", stockTransactionService.findAllSwapTransactions());
        return "swapHistory";
    }

    @GetMapping("/addTransaction")
    public String createBudgetTransaction(Model model) {
        model.addAttribute("types", TransactionType.values());
        model.addAttribute("transaction", new BudgetTransaction(BigDecimal.ZERO, "", "", TransactionType.INCOME, LocalDate.now()));
        return "budgetForm";
    }

    @PostMapping("/addBudgetTransaction")
    public String addBudgetTransaction(BudgetTransaction transaction) {
        budgetTransactionService.save(transaction);
        return "redirect:/cashWallet";
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

    @PostMapping("/tradeStock")
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
        model.addAttribute("action", "sellCrypto");
        BigDecimal balance = budgetTransactionService.getBalanceInPln();
        model.addAttribute("balance", balance);
        model.addAttribute("dollarBalance", budgetTransactionService.getBalanceInPln().divide(BigDecimal.valueOf(4.28), RoundingMode.HALF_UP));
        model.addAttribute("maxValue", stockTransactionService.getAmount(symbol));
        return "formCrypto";
    }

}
