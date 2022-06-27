package pl.ml.wallet.transaction;

import org.springframework.stereotype.Service;
import pl.ml.wallet.stockTransaction.stock.Stock;
import pl.ml.wallet.stockTransaction.stock.StockService;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetTransactionService {
    private BudgetTransactionRepository budgetTransactionRepository;
    private StockService stockService;

    public BudgetTransactionService(BudgetTransactionRepository budgetTransactionRepository, StockService stockService) {
        this.budgetTransactionRepository = budgetTransactionRepository;
        this.stockService = stockService;
    }

    public List<BudgetTransaction> findAll() {
        List<BudgetTransaction> result = budgetTransactionRepository.findAll();
        result.sort(new BudgetDateComparator());
        return result;
    }

    public BigDecimal getBalanceInPln() {
        List<BudgetTransaction> all = budgetTransactionRepository.findAll();
        List<BudgetTransaction> incomes = all.stream().filter(t -> t.getType().equals(TransactionType.INCOME)).collect(Collectors.toList());
        List<BudgetTransaction> expenses = all.stream().filter(t -> t.getType().equals(TransactionType.EXPENSE)).collect(Collectors.toList());

        BigDecimal incomesValue = incomes.stream().map(BudgetTransaction::getTransactionValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expensesValue = expenses.stream().map(BudgetTransaction::getTransactionValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        return incomesValue.subtract(expensesValue);
    }

    public BigDecimal getMaxAmount(BigDecimal balance, Stock stock) {
        BigDecimal dolarBalance = balance.divide(BigDecimal.valueOf(4.28), MathContext.DECIMAL64);
        return dolarBalance.divide(stock.getCurrentPrice(), 6, RoundingMode.HALF_UP);
    }

    public void save(BudgetTransaction budgetTransaction) {
        budgetTransactionRepository.save(budgetTransaction);
    }

    public BigDecimal getSavingsAmount() {
        return BigDecimal.valueOf(15762);
    }

}
