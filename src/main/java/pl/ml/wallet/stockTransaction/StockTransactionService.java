package pl.ml.wallet.stockTransaction;

import org.springframework.stereotype.Service;
import pl.ml.wallet.stockTransaction.dto.*;
import pl.ml.wallet.stockTransaction.stock.Stock;
import pl.ml.wallet.stockTransaction.stock.StockService;
import pl.ml.wallet.stockTransaction.stock.dto.StockMarketDto;
import pl.ml.wallet.transaction.BudgetTransaction;
import pl.ml.wallet.transaction.BudgetTransactionService;
import pl.ml.wallet.transaction.TransactionType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StockTransactionService {
    private StockTransactionRepository stockTransactionRepository;
    private StockService stockService;
    private BudgetTransactionService budgetTransactionService;

    public StockTransactionService(StockTransactionRepository stockTransactionRepository, StockService stockService, BudgetTransactionService budgetTransactionService) {
        this.stockTransactionRepository = stockTransactionRepository;
        this.stockService = stockService;
        this.budgetTransactionService = budgetTransactionService;
    }

    public AccountProfileDto findAllTransactionToProfile(String symbol, String range) {
        List<TransactionOwnedDto> transactions;

        if (range == null) {
            range = "1D";
        }
//opakować to w metody i ostatni też.
        switch (range) {
            case "1h":
                transactions = stockTransactionRepository.findAllTransactionToProfileWith1hChange(symbol);
                break;
            case "1D":
                transactions = stockTransactionRepository.findAllTransactionToProfileWith1dChange(symbol);
                break;
            case "1W":
                transactions = stockTransactionRepository.findAllTransactionToProfileWith7dChange(symbol);
                break;
            case "1M":
                transactions = stockTransactionRepository.findAllTransactionToProfileWith30dChange(symbol);
                break;
            case "3M":
                transactions = stockTransactionRepository.findAllTransactionToProfileWith90dChange(symbol);
                break;
            case "max":
                transactions = stockTransactionRepository.findAllTransactionToProfileWithAllChange(symbol);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + range);
        }

        if (range.equals("max")) {
//            s.name AS name,
//            s.symbol AS symbol,
//            s.favourite AS favourite
//            t.amount AS amount,
//            s.currentPrice AS currentPrice,
//            t.price AS buyPrice
            BigDecimal amount = transactions.stream().map(TransactionOwnedDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal currentPrice = transactions.get(0).getCurrentPrice();
            BigDecimal currentValue = amount.multiply(currentPrice);

//            to musi być strumień
            BigDecimal buyValue = transactions.stream().map(t -> t.getAmount().multiply(t.getBuyPrice())).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal valueChange = currentValue.subtract(buyValue);

//            kupiliśmy 100, dzisiaj warte 75 czyli -25%
//            kupiliśmy 200, dzisiaj warte 220 czyli +10%

            BigDecimal m1 = currentValue.multiply(BigDecimal.valueOf(100));
            BigDecimal divide = m1.divide(buyValue, MathContext.DECIMAL64);

            BigDecimal percentChange = divide.subtract(BigDecimal.valueOf(100));

//            String name, String symbol, BigDecimal amount, BigDecimal value, Double percentChange, BigDecimal valueChange, boolean favourite, BigDecimal currentPrice
//        valuechange i percentChagne

            return new AccountProfileDto(transactions.get(0).getName(), transactions.get(0).getSymbol(), amount,
                    amount.multiply(currentPrice), percentChange.doubleValue(), valueChange, transactions.get(0).getFavourite(), currentPrice);

        } else {

            BigDecimal amount = transactions.stream().map(TransactionOwnedDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal currentPrice = transactions.get(0).getCurrentPrice();
            BigDecimal value = amount.multiply(currentPrice);
            BigDecimal valueChange = value.multiply(BigDecimal.valueOf(transactions.get(0).getPercentChange())).divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);

            return new AccountProfileDto(transactions.get(0).getName(), transactions.get(0).getSymbol(), amount,
                        amount.multiply(currentPrice), transactions.get(0).getPercentChange(), valueChange, transactions.get(0).getFavourite(), currentPrice);

        }

    }

    private  List<TransactionOwnedDto> getDtoWithAllChange(String symbol) {
        return stockTransactionRepository.findAllTransactionToProfileWithAllChange(symbol);
    }

    public List<TransactionOwnedDto> findAllOwnedAccounts(String range) {
        if (range == null) {
            range = "1D";
        }

        switch (range) {
            case "1h":
                return stockTransactionRepository.findAllMyAccountsWith1hChange();
            case "1D":
                return stockTransactionRepository.findAllMyAccountsWith1dChange();
            case "1W":
                return stockTransactionRepository.findAllMyAccountsWith7dChange();
            case "1M":
                return stockTransactionRepository.findAllMyAccountsWith30dChange();
            case "3M":
                return stockTransactionRepository.findAllMyAccountsWith90dChange();
            default:
                throw new IllegalStateException("Unexpected value: " + range);
        }

    }

//    zrobić pod metode która robi to na obiekcie a tą metodę zrobić w strumieniu
//    public List<AccountDto> toAccountDto(List<TransactionOwnedDto> list) {
//        Map<String, List<TransactionOwnedDto>> collect = list.stream().collect(Collectors.groupingBy(TransactionOwnedDto::getSymbol));
//        List<AccountDto> result = new ArrayList<>();
//        Set<String> keySet = collect.keySet();
//        keySet.forEach(k -> {
//            List<TransactionOwnedDto> list1 = collect.get(k);
//            BigDecimal sumOfAmount = list1.stream().map(TransactionOwnedDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
//            result.add(new AccountDto(list1.get(0).getName(), list1.get(0).getSymbol(), sumOfAmount,
//                    list1.get(0).getCurrentPrice().multiply(sumOfAmount), list1.get(0).getPercentChange()));
//        });
//
//        return result;
//    }

    public List<AccountDto> toAccountDto(List<TransactionOwnedDto> list) {
        Map<String, List<TransactionOwnedDto>> collect = list.stream().collect(Collectors.groupingBy(TransactionOwnedDto::getSymbol));
        List<AccountDto> result = new ArrayList<>();
        Set<String> keySet = collect.keySet();
        keySet.forEach(k -> {
            List<TransactionOwnedDto> list1 = collect.get(k);
            BigDecimal sumOfAmount = list1.stream().map(TransactionOwnedDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            result.add(new AccountDto(list1.get(0).getName(), list1.get(0).getSymbol(), sumOfAmount,
                    list1.get(0).getCurrentPrice().multiply(sumOfAmount), list1.get(0).getPercentChange()));
        });

        return result;
    }

    public BigDecimal getTotalBalance() {
        List<TransactionBalanceDto> allStocks = stockTransactionRepository.findAllToGetTotalBalance();
        return allStocks.stream().map(t -> {
            BigDecimal currentPrice = t.getCurrentPrice();
            BigDecimal amount = t.getAmount();
            return currentPrice.multiply(amount);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getPercentageProfit(String range) {
        BigDecimal multiply = getProfit(range).multiply(BigDecimal.valueOf(100));
        BigDecimal totalBalance = getTotalBalance();
        return multiply.divide(totalBalance, RoundingMode.HALF_UP);
    }

    public BigDecimal getProfitAccount(String range) {
        if (range == null) {
            range = "1D";
        }

        switch (range) {
            case "1h":
                return getProfitOfStock(stockTransactionRepository.findAllToGet1hProfit()).setScale(2, RoundingMode.HALF_UP);
            case "1D":
                return getProfitOfStock(stockTransactionRepository.findAllToGet24hProfit()).setScale(2, RoundingMode.HALF_UP);
            case "1W":
                return getProfitOfStock(stockTransactionRepository.findAllToGet7dProfit()).setScale(2, RoundingMode.HALF_UP);
            case "1M":
                return getProfitOfStock(stockTransactionRepository.findAllToGet30dProfit()).setScale(2, RoundingMode.HALF_UP);
            case "3M":
                return getProfitOfStock(stockTransactionRepository.findAllToGet90dProfit()).setScale(2, RoundingMode.HALF_UP);
            case "max":
                return getFinalProfitOfStock(stockTransactionRepository.findAllToGetWholeProfit()).setScale(2, RoundingMode.HALF_UP);
            default:
                return null;
        }
    }

    public BigDecimal getProfit(String range) {
        if (range == null) {
            range = "1D";
        }

        switch (range) {
            case "1h":
                return getProfitOfStock(stockTransactionRepository.findAllToGet1hProfit()).setScale(2, RoundingMode.HALF_UP);
            case "1D":
                return getProfitOfStock(stockTransactionRepository.findAllToGet24hProfit()).setScale(2, RoundingMode.HALF_UP);
            case "1W":
                return getProfitOfStock(stockTransactionRepository.findAllToGet7dProfit()).setScale(2, RoundingMode.HALF_UP);
            case "1M":
                return getProfitOfStock(stockTransactionRepository.findAllToGet30dProfit()).setScale(2, RoundingMode.HALF_UP);
            case "3M":
                return getProfitOfStock(stockTransactionRepository.findAllToGet90dProfit()).setScale(2, RoundingMode.HALF_UP);
            case "max":
                return getFinalProfitOfStock(stockTransactionRepository.findAllToGetWholeProfit()).setScale(2, RoundingMode.HALF_UP);
            default:
                return null;
        }

    }

    private BigDecimal getFinalProfitOfStock(List<TransactionProfitDto> transactions) {
        return transactions.stream().map(t -> {
            BigDecimal amount = t.getAmount();
            BigDecimal currentPrice = t.getCurrentPrice();
            BigDecimal buyPrice = t.getBuyPrice();
            BigDecimal value = amount.multiply(currentPrice);
            BigDecimal buyValue = amount.multiply(buyPrice);
            return value.subtract(buyValue);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getProfitOfStock(List<TransactionProfitDto> transactions) {
        return transactions.stream().map(t -> {
            BigDecimal amount = t.getAmount();
            BigDecimal currentPrice = t.getCurrentPrice();
            BigDecimal percentChange = t.getPercentChange();
            BigDecimal value = amount.multiply(currentPrice);
            BigDecimal result = value.multiply(percentChange);
            BigDecimal divide = result.divide(BigDecimal.valueOf(100));
            return divide;
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void buyCrypto(Double amount, String symbol) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stockTransactionRepository.save(new StockTransaction(LocalDate.now(), stock,
                BigDecimal.valueOf(amount), stock.getCurrentPrice(), StockTransactionType.BUY));

        budgetTransactionService.save(new BudgetTransaction(BigDecimal.valueOf(amount).multiply(stock.getCurrentPrice()), "Buy " + stock.getSymbol(), "PLN -> " + stock.getSymbol() + " " + amount  + " ", TransactionType.EXPENSE, LocalDate.now()));
        //zwraca true/false?
    }

    public List<StockMarketDto> findAllOwnedStockNames() {
        return stockTransactionRepository.findAllOwnedStocksName();
    }

    public BigDecimal getAmount(String symbol) {
        return stockTransactionRepository.findAllBySymbol(symbol).stream()
                .map(TransactionOwnedDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
