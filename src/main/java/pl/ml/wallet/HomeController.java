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

    @GetMapping("/cashWallet")
    public String cashWallet(@RequestParam (required = false) String range,
                             @RequestParam (required = false) String type,
                             Model model) {
        model.addAttribute("transactions", budgetTransactionService.findAllByType(type));
        model.addAttribute("balance", budgetTransactionService.getCashBalance());
        model.addAttribute("range", stockService.getRange(range));
        model.addAttribute("type", stockService.getType(type));
        model.addAttribute("profit", budgetTransactionService.getProfit(range));
        return "cashWallet";
    }

    @GetMapping("/cryptoWallet")
    public String cryptoWallet(@RequestParam (required = false) String range,
                         Model model) {
        model.addAttribute("accounts",
                stockTransactionService.toAccountDto(stockTransactionService.findAllOwnedAccounts(range)));
        model.addAttribute("balance", stockTransactionService.getCryptoBalance());
        model.addAttribute("percentageProfit", stockTransactionService.getPercentageProfit(range));
        model.addAttribute("profit", stockTransactionService.getCryptoProfit(range));
        return "cryptoWallet";
    }

    @PostMapping("/swapCrypto")
    public String swapCrypto(@RequestParam String from,
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
    public String swapCryptoForm(@RequestParam String from,
                             @RequestParam String to,
                             Model model) {

        model.addAttribute("stockAmount", stockTransactionService.getAmount(from));
        Stock sellStock = stockService.findBySymbol(from).orElseThrow();
        Stock buyStock = stockService.findBySymbol(to).orElseThrow();
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
        model.addAttribute("currentPrice", stock.getCurrentPrice());
        model.addAttribute("stocks",  stockService.findAllStockNames());
        model.addAttribute("form", "Buy");
        model.addAttribute("action", "buyCrypto");
        BigDecimal balance = budgetTransactionService.getCashBalance();
        model.addAttribute("balance", balance);
        model.addAttribute("dollarBalance", CurrencyExchange.toUsd(budgetTransactionService.getCashBalance()));
        model.addAttribute("maxValue", budgetTransactionService.getMaxAmount(balance, stock));
        return "formCrypto";
    }

    @GetMapping("/sellCrypto")
    public String sellCrypto(@RequestParam String symbol, Model model) {
        model.addAttribute("form", "Sell");
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        model.addAttribute("symbol", stock.getSymbol());
        model.addAttribute("currentPrice", stock.getCurrentPrice());
        model.addAttribute("currentStock", stock.getName());
        model.addAttribute("stocks",  stockTransactionService.findAllOwnedStockNames());
        model.addAttribute("form", "Sell");
        model.addAttribute("action", "sellCrypto");
        model.addAttribute("balance", budgetTransactionService.getCashBalance());
        model.addAttribute("dollarBalance", CurrencyExchange.toUsd(budgetTransactionService.getCashBalance()));
        model.addAttribute("maxValue", stockTransactionService.getAmount(symbol));
        return "formCrypto";
    }

    @PostMapping("/tradeStock")
    public String buyBtc(@RequestParam(required = false) String symbol,
                         @RequestParam String transactionType,
                         @RequestParam(required = false) Double amount) {
        stockTransactionService.tradeStock(transactionType, symbol, amount);
        return "redirect:/" + transactionType + "?symbol=" + symbol;
    }


}
