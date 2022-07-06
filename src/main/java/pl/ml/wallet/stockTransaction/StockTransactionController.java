package pl.ml.wallet.stockTransaction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.CurrencyExchange;
import pl.ml.wallet.stockTransaction.stock.Stock;
import pl.ml.wallet.stockTransaction.stock.StockService;
import pl.ml.wallet.transaction.BudgetTransactionService;

import java.math.BigDecimal;

@Controller
public class StockTransactionController {
    private StockTransactionService stockTransactionService;
    private StockService stockService;
    private BudgetTransactionService budgetTransactionService;

    public StockTransactionController(StockTransactionService stockTransactionService, StockService stockService, BudgetTransactionService budgetTransactionService) {
        this.stockTransactionService = stockTransactionService;
        this.stockService = stockService;
        this.budgetTransactionService = budgetTransactionService;
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
                               Model model) {
        model.addAttribute("range", stockService.getRange(range));
        model.addAttribute("type", stockTransactionService.getType(type));
        model.addAttribute("stock", stockTransactionService.findAllTransactionToProfile(symbol, range));
        model.addAttribute("transactions",
                stockTransactionService.findAllTransactionsByStock(stockService.findBySymbol(symbol).orElseThrow().getId(), type));
        return "accountStock";
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
}
