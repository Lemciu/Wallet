package pl.ml.wallet.transaction;

import org.springframework.stereotype.Service;
import pl.ml.wallet.comparator.StockOwnedValue;
import pl.ml.wallet.stock.Stock;
import pl.ml.wallet.stock.StockService;
import pl.ml.wallet.stock.api.StockDto;
import pl.ml.wallet.transaction.dto.TransactionOwnedDto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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

    public List<TransactionOwnedDto> getAllOwnedTransactions() {
        List<Transaction> all = transactionRepository.findAll();
        Map<Stock, List<Transaction>> collect = all.stream().collect(Collectors.groupingBy(Transaction::getStock));
        List<TransactionOwnedDto> result = new ArrayList<>();
        Set<Stock> stocks = collect.keySet();
        List<StockDto> allStocks = stockService.getAllStocks();
        String name = "";
        allStocks.stream().filter(s -> s.getName().equals(name)).collect(Collectors.toList());
//        s.getCurrentPrice().doubleValue() // konstruktor
        stocks.forEach(s -> {
            List<Transaction> allByStock = transactionRepository.findAllByStock(s);
            BigDecimal amount = allByStock.stream().map(Transaction::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);//tu zrobic częsci dziesietne
            result.add(new TransactionOwnedDto(s.getName(), findStockByName(allStocks, s.getSymbol()).getQuote().getUsd().getPrice().doubleValue(),
                    getAllocation(getSumValueOfStock(), s.getCurrentPrice()
                            .multiply(amount)
                            .setScale(2, RoundingMode.HALF_UP)).doubleValue(),
                    getProfit(getBuyValue(s),
                            s.getCurrentPrice().multiply(amount).setScale(2, RoundingMode.HALF_UP)).doubleValue(),
                    getPercentageProfit(getBuyValue(s), s.getCurrentPrice()
                            .multiply(amount)
                            .setScale(2, RoundingMode.HALF_UP)), amount, s.getCurrentPrice()
                    .multiply(amount).setScale(2, RoundingMode.HALF_UP)));
        });
        result.sort(new StockOwnedValue());
        AtomicInteger id = new AtomicInteger(1);
        result.forEach(t -> t.setId(id.getAndIncrement()));
        return result;
    }

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

    private BigDecimal getProfit(BigDecimal buyValue, BigDecimal currentValue) {
        return currentValue.subtract(buyValue);
    }

    private double getPercentageProfit(BigDecimal buyValue, BigDecimal currentValue) {
        BigDecimal multiply = currentValue.multiply(BigDecimal.valueOf(100));
        BigDecimal divide = multiply.divide(buyValue, 2, RoundingMode.HALF_UP);
        return divide.subtract(BigDecimal.valueOf(100)).doubleValue();
    }

    public StockDto findStockByName(List<StockDto> stocks, String symbol) {
        List<StockDto> collect = stocks.stream().filter(s -> s.getSymbol().equals(symbol)).collect(Collectors.toList());
        if (collect.isEmpty()) {
            return stocks.get(0);// tu poprawić
        } else {
            return collect.get(0);
        }
    }


}
