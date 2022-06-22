package pl.ml.wallet.stockTransaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.ml.wallet.stockTransaction.dto.TransactionBalanceDto;
import pl.ml.wallet.stockTransaction.dto.TransactionOwnedDto;
import pl.ml.wallet.stockTransaction.dto.TransactionProfitDto;
import pl.ml.wallet.stockTransaction.stock.dto.StockMarketDto;

import java.util.List;
import java.util.Optional;

public interface StockTransactionRepository extends JpaRepository<StockTransaction, Long> {

    List<StockTransaction> findAllByType(StockTransactionType type);

    @Query("SELECT DISTINCT s.name AS name, s.symbol AS symbol FROM StockTransaction t JOIN Stock s ON s.id = t.stock.id")
    List<StockMarketDto> findAllOwnedStocksName();

    Optional<StockTransaction> findById(Long id);

    @Query("SELECT t.amount AS amount, s.currentPrice AS currentPrice " +
            "FROM StockTransaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionBalanceDto> findAllToGetTotalBalance();

    @Query("SELECT t.amount AS amount, s.currentPrice AS currentPrice, t.price AS buyPrice " +
            "FROM StockTransaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGetWholeProfit();

    @Query("SELECT s.id AS id, t.amount AS amount, s.percentChange1H AS percentChange, s.currentPrice AS currentPrice " +
            "FROM StockTransaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGet1hProfit();

    @Query("SELECT s.id AS id, t.amount AS amount, s.percentChange24H AS percentChange, s.currentPrice AS currentPrice " +
            "FROM StockTransaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGet24hProfit();

    @Query("SELECT s.id AS id, t.amount AS amount, s.percentChange7D AS percentChange, s.currentPrice AS currentPrice " +
            "FROM StockTransaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGet7dProfit();

    @Query("SELECT s.id AS id, t.amount AS amount, s.percentChange30D AS percentChange, s.currentPrice AS currentPrice " +
            "FROM StockTransaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGet30dProfit();

    @Query("SELECT s.id AS id, t.amount AS amount, s.percentChange90D AS percentChange, s.currentPrice AS currentPrice " +
            "FROM StockTransaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionProfitDto> findAllToGet90dProfit();

    @Query("SELECT s.name AS name, s.symbol AS symbol, t.amount AS amount, s.currentPrice AS currentPrice, s.percentChange1H AS percentChange FROM Stock s JOIN StockTransaction t ON s.id = t.stock.id")
    List<TransactionOwnedDto> findAllMyAccountsWith1hChange();

    @Query("SELECT s.name AS name, s.symbol AS symbol, t.amount AS amount, s.currentPrice AS currentPrice, s.percentChange24H AS percentChange FROM Stock s JOIN StockTransaction t ON s.id = t.stock.id")
    List<TransactionOwnedDto> findAllMyAccountsWith1dChange();

    @Query("SELECT s.name AS name, s.symbol AS symbol, t.amount AS amount, s.currentPrice AS currentPrice, s.percentChange7D AS percentChange FROM Stock s JOIN StockTransaction t ON s.id = t.stock.id")
    List<TransactionOwnedDto> findAllMyAccountsWith7dChange();

    @Query("SELECT s.name AS name, s.symbol AS symbol, t.amount AS amount, s.currentPrice AS currentPrice, s.percentChange30D AS percentChange FROM Stock s JOIN StockTransaction t ON s.id = t.stock.id")
    List<TransactionOwnedDto> findAllMyAccountsWith30dChange();

    @Query("SELECT s.name AS name, s.symbol AS symbol, t.amount AS amount, s.currentPrice AS currentPrice, s.percentChange90D AS percentChange FROM Stock s JOIN StockTransaction t ON s.id = t.stock.id")
    List<TransactionOwnedDto> findAllMyAccountsWith90dChange();

    @Query("SELECT t.amount AS amount, s.symbol AS symbol FROM StockTransaction t JOIN Stock s ON t.stock.id = s.id WHERE s.symbol = ?#{[0]}")
    List<TransactionOwnedDto> findAllBySymbol(String symbol);

    @Query("SELECT s.name AS name, s.symbol AS symbol, s.favourite AS favourite, t.amount AS amount, s.percentChange1H AS percentChange, s.currentPrice AS currentPrice FROM StockTransaction t LEFT JOIN Stock s ON s.id = t.stock.id WHERE s.symbol = ?#{[0]}")
    List<TransactionOwnedDto> findAllTransactionToProfileWith1hChange(String symbol);

    @Query("SELECT s.name AS name, s.symbol AS symbol, s.favourite AS favourite, t.amount AS amount, s.percentChange24H AS percentChange, s.currentPrice AS currentPrice FROM StockTransaction t LEFT JOIN Stock s ON s.id = t.stock.id WHERE s.symbol = ?#{[0]}")
    List<TransactionOwnedDto> findAllTransactionToProfileWith1dChange(String symbol);
//
    @Query("SELECT s.name AS name, s.symbol AS symbol, s.favourite AS favourite, t.amount AS amount, s.percentChange7D AS percentChange, s.currentPrice AS currentPrice FROM StockTransaction t LEFT JOIN Stock s ON s.id = t.stock.id WHERE s.symbol = ?#{[0]}")
    List<TransactionOwnedDto> findAllTransactionToProfileWith7dChange(String symbol);

    @Query("SELECT s.name AS name, s.symbol AS symbol, s.favourite AS favourite, t.amount AS amount, s.percentChange30D AS percentChange, s.currentPrice AS currentPrice FROM StockTransaction t LEFT JOIN Stock s ON s.id = t.stock.id WHERE s.symbol = ?#{[0]}")
    List<TransactionOwnedDto> findAllTransactionToProfileWith30dChange(String symbol);

    @Query("SELECT s.name AS name, s.symbol AS symbol, s.favourite AS favourite, t.amount AS amount, s.percentChange90D AS percentChange, s.currentPrice AS currentPrice FROM StockTransaction t LEFT JOIN Stock s ON s.id = t.stock.id WHERE s.symbol = ?#{[0]}")
    List<TransactionOwnedDto> findAllTransactionToProfileWith90dChange(String symbol);

    @Query("SELECT s.name AS name, s.symbol AS symbol, s.favourite AS favourite, t.amount AS amount, s.currentPrice AS currentPrice, t.price AS buyPrice FROM StockTransaction t LEFT JOIN Stock s ON s.id = t.stock.id WHERE s.symbol = ?#{[0]}")
    List<TransactionOwnedDto> findAllTransactionToProfileWithAllChange(String symbol);

}
