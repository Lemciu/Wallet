package pl.ml.wallet.transaction;

import org.springframework.stereotype.Service;
import pl.ml.wallet.stock.Stock;
import pl.ml.wallet.stock.StockService;
import pl.ml.wallet.stock.api.StockDto;
import pl.ml.wallet.transaction.dto.TransactionBalanceDto;
import pl.ml.wallet.transaction.dto.TransactionProfitDto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private StockService stockService;

    public TransactionService(TransactionRepository transactionRepository, StockService stockService) {
        this.transactionRepository = transactionRepository;
        this.stockService = stockService;
    }

    public BigDecimal getCurrentSaldo() {
        List<BigDecimal> reduce1 = transactionRepository.findAllToSaldo().stream().map(t -> {
            BigDecimal a = t.getCurrentPrice();
            BigDecimal b = t.getAmount();
            BigDecimal c = a.multiply(b);
            return c;
        }).collect(Collectors.toList());
        return reduce1.stream().reduce(BigDecimal.ZERO, BigDecimal::add).setScale(2, RoundingMode.HALF_UP);

    }

    public BigDecimal getInvestedValue() {
        MathContext m = new MathContext(2);
        List<BigDecimal> reduce1 = transactionRepository.findAllToSaldo().stream().map(t -> {
            BigDecimal a = t.getBuyPrice();
            BigDecimal b = t.getAmount();
            BigDecimal c = a.multiply(b, m);
            return c;
        }).collect(Collectors.toList());
        BigDecimal reduce = reduce1.stream().reduce(BigDecimal.ZERO, BigDecimal::add);
        return reduce;
    }

    public double getPercentageProfitSaldo() {
        double currentSaldo = getCurrentSaldo().doubleValue();
        double investedValue = getInvestedValue().doubleValue();
        double result = ((currentSaldo * 100) / investedValue) - 100;
        int temp = (int) (result * 100);
        double shortDouble = ((double) temp) / 100;
        return shortDouble;
    }

    public BigDecimal getProfitSaldo() {
        return getCurrentSaldo().subtract(getInvestedValue());
    }

//    public List<TransactionOwnedDto> getAllOwnedTransactions() {
//        List<Transaction> all = transactionRepository.findAll();
//        Map<Stock, List<Transaction>> collect = all.stream().collect(Collectors.groupingBy(Transaction::getStock));
//        List<TransactionOwnedDto> result = new ArrayList<>();
//        Set<Stock> stocks = collect.keySet();
//        List<StockDto> allStocks = stockService.getAllStocks();
//        String name = "";
//        allStocks.stream().filter(s -> s.getName().equals(name)).collect(Collectors.toList());
////        s.getCurrentPrice().doubleValue() // konstruktor
//        stocks.forEach(s -> {
//            List<Transaction> allByStock = transactionRepository.findAllByStock(s);
//            BigDecimal amount = allByStock.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);//tu zrobic częsci dziesietne
//            result.add(new TransactionOwnedDto(s.getName(), findStockByName(allStocks, s.getSymbol()).getQuote().getUsd().getPrice().doubleValue(),
//                    getAllocation(getSumValueOfStock(), s.getCurrentPrice()
//                            .multiply(amount)
//                            .setScale(2, RoundingMode.HALF_UP)).doubleValue(),
//                    getProfit(getBuyValue(s),
//                            s.getCurrentPrice().multiply(amount).setScale(2, RoundingMode.HALF_UP)).doubleValue(),
//                    getPercentageProfit(getBuyValue(s), s.getCurrentPrice()
//                            .multiply(amount)
//                            .setScale(2, RoundingMode.HALF_UP)), amount, s.getCurrentPrice()
//                    .multiply(amount).setScale(2, RoundingMode.HALF_UP)));
//        });
//        result.sort(new StockOwnedValue());
//        AtomicInteger id = new AtomicInteger(1);
//        result.forEach(t -> t.setId(id.getAndIncrement()));
//        return result;
//    }

    private BigDecimal getBuyValue(Stock stock) {
        return transactionRepository.findAllByStock(stock).stream().map(t -> t.getBuyPrice()
                .multiply(t.getAmount()).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getSumValueOfStock() {
        return transactionRepository.findAll().stream().map(t -> t.getStock().getCurrentPrice()
                .multiply(t.getAmount()).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getAllocation(BigDecimal sumValue, BigDecimal stockValue) {
        return stockValue.multiply(BigDecimal.valueOf(100)).divide(sumValue, 2, RoundingMode.HALF_UP);
    }

//    private BigDecimal getProfit(BigDecimal buyValue, BigDecimal currentValue) {
//        return currentValue.subtract(buyValue);
//    }
//
//    private double getPercentageProfit(BigDecimal buyValue, BigDecimal currentValue) {
//        BigDecimal multiply = currentValue.multiply(BigDecimal.valueOf(100));
//        BigDecimal divide = multiply.divide(buyValue, 2, RoundingMode.HALF_UP);
//        return divide.subtract(BigDecimal.valueOf(100)).doubleValue();
//    }

    public StockDto findStockByName(List<StockDto> stocks, String symbol) {
        List<StockDto> collect = stocks.stream().filter(s -> s.getSymbol().equals(symbol)).collect(Collectors.toList());
        if (collect.isEmpty()) {
            return stocks.get(0);
        } else {
            return collect.get(0);
        }
    }

    public BigDecimal getTotalBalance() {
        List<TransactionBalanceDto> allStocks = transactionRepository.findAllToGetTotalBalance();
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

    public BigDecimal getProfit(String range) {
        if (range == null) {
            range = "1D";
        }

        switch (range) {
            case "1h":
                return getReduceOneToRename(transactionRepository.findAllToGet1hProfit()).setScale(2, RoundingMode.HALF_UP);
            case "1D":
                return getReduceOneToRename(transactionRepository.findAllToGet24hProfit()).setScale(2, RoundingMode.HALF_UP);
            case "1W":
                return getReduceOneToRename(transactionRepository.findAllToGet7dProfit()).setScale(2, RoundingMode.HALF_UP);
            case "1M":
                return getReduceOneToRename(transactionRepository.findAllToGet30dProfit()).setScale(2, RoundingMode.HALF_UP);
            case "3M":
                return getReduceOneToRename(transactionRepository.findAllToGet90dProfit()).setScale(2, RoundingMode.HALF_UP);
            case "max":
                return getReduceToWholeRename(transactionRepository.findAllToGetWholeProfit()).setScale(2, RoundingMode.HALF_UP);
            default:
                return null;
        }

    }

    private BigDecimal getReduceToWholeRename(List<TransactionProfitDto> transactions) {
        return transactions.stream().map(t -> {
            BigDecimal amount = t.getAmount();
            BigDecimal currentPrice = t.getCurrentPrice();
            BigDecimal buyPrice = t.getBuyPrice();
            BigDecimal value = amount.multiply(currentPrice);
            BigDecimal buyValue = amount.multiply(buyPrice);
            return value.subtract(buyValue);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getReduceOneToRename(List<TransactionProfitDto> transactions) {
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


}
