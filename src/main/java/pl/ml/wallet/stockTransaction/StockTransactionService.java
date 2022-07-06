package pl.ml.wallet.stockTransaction;

import org.springframework.stereotype.Service;
import pl.ml.wallet.CurrencyExchange;
import pl.ml.wallet.stockTransaction.dto.*;
import pl.ml.wallet.stockTransaction.stock.*;
import pl.ml.wallet.stockTransaction.stock.comparator.SwapDateComparator;
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
    private SwapTransactionService swapTransactionService;

    public StockTransactionService(StockTransactionRepository stockTransactionRepository, StockService stockService, BudgetTransactionService budgetTransactionService, SwapTransactionService swapTransactionService) {
        this.stockTransactionRepository = stockTransactionRepository;
        this.stockService = stockService;
        this.budgetTransactionService = budgetTransactionService;
        this.swapTransactionService = swapTransactionService;
    }

    public List<TransactionDto> findAllTransactionsByStock(Long id, String type) {
        if (type == null) {
            type = "All";
        }
        List<StockTransaction> transactions = stockTransactionRepository.findAllByStock_Id(id);

        List<StockTransaction> bought = transactions.stream().filter(t -> t.getType().equals(StockTransactionType.BUY)).collect(Collectors.toList());
        List<StockTransaction> sold = transactions.stream().filter(t -> t.getType().equals(StockTransactionType.SELL)).collect(Collectors.toList());

        List<TransactionDto> soldDto = sold.stream().map(t -> new TransactionDto("USD", t.getAmount().multiply(t.getPrice()).negate().doubleValue(), t.getStock().getSymbol(), t.getAmount().negate().doubleValue(), t.getDate())).collect(Collectors.toList());
        List<TransactionDto> boughtDto = bought.stream().map(t -> new TransactionDto(t.getStock().getSymbol(), t.getAmount().doubleValue(), "USD", t.getAmount().multiply(t.getPrice()).doubleValue(), t.getDate())).collect(Collectors.toList());

        List<StockTransaction> swap = transactions.stream().filter(t -> t.getType().equals(StockTransactionType.SWAP)).collect(Collectors.toList());
        List<SwapTransaction> allSwaps = swapTransactionService.findAll();
        List<Long> collect = swap.stream().map(StockTransaction::getId).collect(Collectors.toList());
        List<TransactionDto> swapped = allSwaps.stream().filter(s1 -> {
            Long boughtStockTransactionId = s1.getBoughtStockTransactionId();
            Long soldStockTransactionId = s1.getSoldStockTransactionId();
            for (Long id2 : collect) {
                if (id2.equals(boughtStockTransactionId) || id2.equals(soldStockTransactionId)) {
                    return true;
                }
            }
            return false;
        }).map(s -> {
            Long boughtStockTransactionId = s.getBoughtStockTransactionId();
            Long soldStockTransactionId = s.getSoldStockTransactionId();
            StockTransaction buyTransaction = findById(boughtStockTransactionId);
            StockTransaction sellTransaction = findById(soldStockTransactionId);
            return new TransactionDto(buyTransaction.getStock().getSymbol(),
                    buyTransaction.getAmount().doubleValue(), sellTransaction.getStock().getSymbol(),
                    sellTransaction.getAmount().negate().doubleValue(), sellTransaction.getDate());
        }).collect(Collectors.toList());

        List<TransactionDto> result = new ArrayList<>();
        switch (type) {
            case "Crypto":
                result.addAll(swapped);
                break;
            case "Fiat":
                result.addAll(boughtDto);
                result.addAll(soldDto);
                break;
            case "All":
                result.addAll(boughtDto);
                result.addAll(soldDto);
                result.addAll(swapped);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        result.sort(new SwapDateComparator());
        return result;
    }

    public void tradeStock(String transactionType, String symbol, Double amount) {
        switch (transactionType) {
            case "buyCrypto":
                buyCrypto(amount, symbol);
                break;
            case "sellCrypto":
                sellCrypto(amount, symbol);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + transactionType);
        }

    }

    public List<TransactionDto> findAllSwapTransactions() {
        List<SwapTransaction> all = swapTransactionService.findAll();

        List<TransactionDto> result = all.stream().map(s -> {
            Long boughtStockTransactionId = s.getBoughtStockTransactionId();
            Long soldStockTransactionId = s.getSoldStockTransactionId();
            StockTransaction buyTransaction = findById(boughtStockTransactionId);
            StockTransaction sellTransaction = findById(soldStockTransactionId);

            return new TransactionDto(buyTransaction.getStock().getSymbol(),
                    buyTransaction.getAmount().doubleValue(), sellTransaction.getStock().getSymbol(),
                    sellTransaction.getAmount().negate().doubleValue(), sellTransaction.getDate());
        }).collect(Collectors.toList());
        result.sort(new SwapDateComparator());
        return result;
    }

    public StockTransaction findById(Long id) {
        return stockTransactionRepository.findById(id).orElseThrow();
    }

    public AccountProfileDto findAllTransactionToProfile(String symbol, String range) {
        List<TransactionOwnedDto> transactions;

        if (range == null) {
            range = "1D";
        }
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
            BigDecimal amount = transactions.stream().map(TransactionOwnedDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal currentPrice = transactions.get(0).getCurrentPrice();
            BigDecimal currentValue = amount.multiply(currentPrice);
            BigDecimal buyValue = transactions.stream().map(t -> t.getAmount().multiply(t.getBuyPrice())).reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal valueChange = currentValue.subtract(buyValue);

            BigDecimal m1 = currentValue.multiply(BigDecimal.valueOf(100));
            BigDecimal divide = m1.divide(buyValue, MathContext.DECIMAL64);
            if (valueChange.doubleValue() < 0) {
                divide = divide.negate();
            }

            return new AccountProfileDto(transactions.get(0).getName(), transactions.get(0).getSymbol(), amount,
                    amount.multiply(currentPrice), divide.doubleValue(), valueChange, transactions.get(0).getFavourite(), currentPrice);

        } else {

            BigDecimal amount = transactions.stream().map(TransactionOwnedDto::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
            BigDecimal currentPrice = transactions.get(0).getCurrentPrice();
            BigDecimal value = amount.multiply(currentPrice);
            BigDecimal valueChange = value.multiply(BigDecimal.valueOf(transactions.get(0).getPercentChange())).divide(BigDecimal.valueOf(100), MathContext.DECIMAL64);

            return new AccountProfileDto(transactions.get(0).getName(), transactions.get(0).getSymbol(), amount,
                    amount.multiply(currentPrice), transactions.get(0).getPercentChange(), valueChange, transactions.get(0).getFavourite(), currentPrice);

        }

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
        result.sort(new ValueComparator());
        return result;
    }

    public BigDecimal getCryptoBalance() {
        return stockTransactionRepository.findAllToGetTotalBalance()
                .stream()
                .map(this::getValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getValue(TransactionBalanceDto transaction) {
        return transaction.getCurrentPrice().multiply(transaction.getAmount());
    }

    public BigDecimal getPercentageProfit(String range) {
        return getCryptoProfit(range).multiply(BigDecimal.valueOf(100)).divide(getCryptoBalance(), RoundingMode.HALF_UP);
    }

    public BigDecimal getCryptoProfit(String range) {
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
                System.out.println("max max2");
                return getFinalProfitOfStock(stockTransactionRepository.findAllToGetWholeProfit()).setScale(2, RoundingMode.HALF_UP);
            default:
                return null;
        }

    }

    private BigDecimal getFinalProfitOfStock(List<TransactionProfitDto> transactions) {
        return transactions.stream().map(t -> {
            BigDecimal value = t.getAmount().multiply(t.getCurrentPrice());
            BigDecimal buyValue = t.getAmount().multiply(t.getBuyPrice());
            return value.subtract(buyValue);
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getProfitOfStock(List<TransactionProfitDto> transactions) {
        return transactions.stream().map(t -> {
            BigDecimal value = t.getAmount().multiply(t.getCurrentPrice());
            BigDecimal valueChange = value.multiply(t.getPercentChange());
            return valueChange.divide(BigDecimal.valueOf(100));
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void sellCrypto(Double amount, String symbol) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();

        stockTransactionRepository.save(new StockTransaction(LocalDate.now(), stock,
                BigDecimal.valueOf(amount).negate(), stock.getCurrentPrice(), StockTransactionType.SELL));

        budgetTransactionService.save(new BudgetTransaction(
                BigDecimal.valueOf(amount).multiply(CurrencyExchange.toPln(stock.getCurrentPrice())),
                "Sell " + stock.getSymbol(), amount + " " + stock.getSymbol() + " -> PLN",
                TransactionType.INCOME, LocalDate.now()));
    }

    public void buyCrypto(Double amount, String symbol) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();

        stockTransactionRepository.save(new StockTransaction(LocalDate.now(), stock,
                BigDecimal.valueOf(amount), stock.getCurrentPrice(), StockTransactionType.BUY));

        budgetTransactionService.save(new BudgetTransaction(
                BigDecimal.valueOf(amount).multiply(CurrencyExchange.toPln(stock.getCurrentPrice())),
                "Buy " + stock.getSymbol(), "PLN -> " + stock.getSymbol() + " " + amount + " ",
                TransactionType.EXPENSE, LocalDate.now()));
    }

    public List<StockMarketDto> findAllOwnedStockNames() {
        return stockTransactionRepository.findAllOwnedStocksName();
    }

    public BigDecimal getAmount(String symbol) {
        return stockTransactionRepository.findAllOwnedDtoBySymbol(symbol).stream()
                .map(TransactionOwnedDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalBalanceInPln() {
        BigDecimal cash = budgetTransactionService.getCashBalance();
        BigDecimal investments = CurrencyExchange.toPln(getCryptoBalance());
        return investments.add(cash);
    }

    public BigDecimal getBalanceInPercent() {
        return budgetTransactionService
                .getCashBalance()
                .multiply(BigDecimal.valueOf(100))
                .divide(getTotalBalanceInPln(), MathContext.DECIMAL64);
    }

    public BigDecimal getInvestmentsInPercent() {
        return CurrencyExchange
                .toPln(getCryptoBalance())
                .multiply(BigDecimal.valueOf(100))
                .divide(getTotalBalanceInPln(), MathContext.DECIMAL64);
    }

    public BigDecimal getRatio(Stock sellStock, Stock buyStock) {
        BigDecimal sellStockCurrentPrice = sellStock.getCurrentPrice();
        BigDecimal buyStockCurrentPrice = buyStock.getCurrentPrice();
        return sellStockCurrentPrice.divide(buyStockCurrentPrice, MathContext.DECIMAL64);
    }

    public void swapCrypto(String from, Double amount, String to, Double rate) {
        Stock sellStock = stockService.findBySymbol(from).orElseThrow();
        Stock buyStock = stockService.findBySymbol(to).orElseThrow();
        BigDecimal result = BigDecimal.valueOf(rate).multiply(BigDecimal.valueOf(amount), MathContext.DECIMAL64);
        StockTransaction boughtStock = stockTransactionRepository.save(new StockTransaction(LocalDate.now(), buyStock, result, buyStock.getCurrentPrice(), StockTransactionType.SWAP));
        StockTransaction soldStock = stockTransactionRepository.save(new StockTransaction(LocalDate.now(), sellStock, BigDecimal.valueOf(amount).negate(), sellStock.getCurrentPrice(), StockTransactionType.SWAP));
        swapTransactionService.add(boughtStock.getId(), soldStock.getId());
    }

    public String getType(String type) {
        if (type == null) {
            type = "All";
        }
        return type;
    }
}
