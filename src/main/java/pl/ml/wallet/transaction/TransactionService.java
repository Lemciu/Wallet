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

    public List<TransactionOwnedDto> findAllOwnedAccounts(String range) {
        if (range == null) {
            range = "1D";
        }

        switch (range) {
            case "1h":
                return transactionRepository.findAllMyAccountsWith1hChange();
            case "1D":
                return transactionRepository.findAllMyAccountsWith1dChange();
            case "1W":
                return transactionRepository.findAllMyAccountsWith7dChange();
            case "1M":
                return transactionRepository.findAllMyAccountsWith30dChange();
            case "3M":
                return transactionRepository.findAllMyAccountsWith90dChange();
            default:
                throw new IllegalStateException("Unexpected value: " + range);
        }

    }

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
