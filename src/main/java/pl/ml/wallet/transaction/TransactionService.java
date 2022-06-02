package pl.ml.wallet.transaction;

import org.springframework.stereotype.Service;
import pl.ml.wallet.stock.Stock;
import pl.ml.wallet.stock.StockService;
import pl.ml.wallet.stock.api.StockDto;
import pl.ml.wallet.transaction.dto.AccountDto;
import pl.ml.wallet.transaction.dto.TransactionBalanceDto;
import pl.ml.wallet.transaction.dto.TransactionOwnedDto;
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

    public List<TransactionOwnedDto> findAllOwnedAccounts() {
        return transactionRepository.findAllMyAccountsWith1hChange();
    }

    public List<AccountDto> toAccountDto(List<TransactionOwnedDto> list) {
//        map
//        póki jest taki sam symbol to niech amount się sumuje
        Map<String, List<TransactionOwnedDto>> collect = list.stream().collect(Collectors.groupingBy(TransactionOwnedDto::getSymbol));

        Set<String> keySet = collect.keySet();
        // iterować to up i


        return null;
    }

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
