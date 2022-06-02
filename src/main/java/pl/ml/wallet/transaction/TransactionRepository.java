package pl.ml.wallet.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.ml.wallet.stock.Stock;
import pl.ml.wallet.transaction.dto.TransactionBalanceDto;
import pl.ml.wallet.transaction.dto.TransactionDto;
import pl.ml.wallet.transaction.dto.TransactionOwnedDto;
import pl.ml.wallet.transaction.dto.TransactionProfitDto;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t.amount AS amount, t.buyPrice AS buyPrice, s.currentPrice AS currentPrice " +
            "FROM Transaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionDto> findAllToSaldo();

    List<Transaction> findAllByStock(Stock stock);

    @Query("SELECT t.amount AS amount, s.currentPrice AS currentPrice " +
            "FROM Transaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionBalanceDto> findAllToGetTotalBalance();

    @Query("SELECT t.amount AS amount, s.currentPrice AS currentPrice, t.buyPrice AS buyPrice " +
            "FROM Transaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGetWholeProfit();

    @Query("SELECT s.id AS id, t.amount AS amount, s.percentChange1H AS percentChange, s.currentPrice AS currentPrice " +
            "FROM Transaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGet1hProfit();

    @Query("SELECT s.id AS id, t.amount AS amount, s.percentChange24H AS percentChange, s.currentPrice AS currentPrice " +
            "FROM Transaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGet24hProfit();

    @Query("SELECT s.id AS id, t.amount AS amount, s.percentChange7D AS percentChange, s.currentPrice AS currentPrice " +
            "FROM Transaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGet7dProfit();

    @Query("SELECT s.id AS id, t.amount AS amount, s.percentChange30D AS percentChange, s.currentPrice AS currentPrice " +
            "FROM Transaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGet30dProfit();

    @Query("SELECT s.id AS id, t.amount AS amount, s.percentChange90D AS percentChange, s.currentPrice AS currentPrice " +
            "FROM Transaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGet90dProfit();

    @Query("SELECT s.name AS name, s.symbol AS symbol, t.amount AS amount, s.currentPrice AS currentPrice, s.percentChange1H AS percentChange FROM Stock s JOIN Transaction t ON s.id = t.stock.id")
    List<TransactionOwnedDto> findAllMyAccountsWith1hChange();

}
