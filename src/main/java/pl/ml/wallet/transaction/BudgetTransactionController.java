package pl.ml.wallet.transaction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stockTransaction.stock.StockService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class BudgetTransactionController {
    private BudgetTransactionService budgetTransactionService;
    private StockService stockService;

    public BudgetTransactionController(BudgetTransactionService budgetTransactionService, StockService stockService) {
        this.budgetTransactionService = budgetTransactionService;
        this.stockService = stockService;
    }

    @GetMapping("/cashWallet")
    public String cashWallet(@RequestParam(required = false) String range,
                             @RequestParam (required = false) String type,
                             Model model) {
        model.addAttribute("transactions", budgetTransactionService.findAllByType(type));
        model.addAttribute("balance", budgetTransactionService.getCashBalance());
        model.addAttribute("range", stockService.getRange(range));
        model.addAttribute("type", stockService.getType(type));
        model.addAttribute("profit", budgetTransactionService.getProfit(range));
        return "cashWallet";
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
}
