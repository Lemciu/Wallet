package pl.ml.wallet.transaction;

import org.springframework.stereotype.Service;
import pl.ml.wallet.CurrencyExchange;
import pl.ml.wallet.stockTransaction.stock.Stock;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetTransactionService {
    private BudgetTransactionRepository budgetTransactionRepository;

    public BudgetTransactionService(BudgetTransactionRepository budgetTransactionRepository) {
        this.budgetTransactionRepository = budgetTransactionRepository;
    }

    public List<BudgetTransaction> findAll() {
        List<BudgetTransaction> result = budgetTransactionRepository.findAll();
        result.sort(new BudgetDateComparator());
        return result;
    }

    private TransactionType getTypeByName(String type) {
        switch (type) {
            case "incomes":
                return TransactionType.INCOME;
            case "expenses":
                return TransactionType.EXPENSE;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public List<BudgetTransaction> findAllByType(String type) {
        if (type == null || type.equals("All")) {
            return findAll();
        } else {
            return budgetTransactionRepository.findAllByType(getTypeByName(type));
        }
    }

    public BigDecimal getCashBalance() {
        List<BudgetTransaction> all = budgetTransactionRepository.findAll();
        List<BudgetTransaction> incomes = all.stream().filter(t -> t.getType().equals(TransactionType.INCOME)).collect(Collectors.toList());
        List<BudgetTransaction> expenses = all.stream().filter(t -> t.getType().equals(TransactionType.EXPENSE)).collect(Collectors.toList());

        BigDecimal incomesValue = incomes.stream().map(BudgetTransaction::getTransactionValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expensesValue = expenses.stream().map(BudgetTransaction::getTransactionValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        return incomesValue.subtract(expensesValue);
    }

    public BigDecimal getMaxAmount(BigDecimal balance, Stock stock) {
        BigDecimal dolarBalance = CurrencyExchange.toUsd(balance);
        return dolarBalance.divide(stock.getCurrentPrice(), 6, RoundingMode.HALF_UP);
    }

    public void save(BudgetTransaction budgetTransaction) {
        budgetTransactionRepository.save(budgetTransaction);
    }

    public BigDecimal getProfit(String range) {
        LocalDate today = LocalDate.now();
        LocalDate result;
        if (range == null) {
            range = "1D";
        }

        switch (range) {
            case "1D":
                result = today.minusDays(1);
                break;
            case "1W":
                result = today.minusDays(7);
                break;
            case "1M":
                result = today.minusDays(30);
                break;
            case "3M":
                result = today.minusDays(90);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + range);
        }
        List<BudgetTransactionDto> transactions = budgetTransactionRepository.findAllToGetProfit(result);
        BigDecimal incomesValue = transactions.stream().filter(t -> t.getType().equals(TransactionType.INCOME)).map(BudgetTransactionDto::getTransactionValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal expensesValue = transactions.stream().filter(t -> t.getType().equals(TransactionType.EXPENSE)).map(BudgetTransactionDto::getTransactionValue).reduce(BigDecimal.ZERO, BigDecimal::add);
        return incomesValue.subtract(expensesValue);
    }
}
