package pl.ml.wallet.stockTransaction.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.ml.wallet.stockTransaction.stock.dto.StockMarketDto;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Query("SELECT name AS name, symbol AS symbol FROM Stock WHERE id <= 10")
    List<StockMarketDto> findAllStocksName();

    Optional<Stock> findById(Long id);

    Optional<Stock> findBySymbol(String symbol);

    @Query("SELECT name AS name, currentPrice AS currentPrice,symbol AS symbol, percentChange24H AS percentChange FROM Stock " +
            "WHERE favourite = true")
    List<StockMarketDto> findAllByFavouriteIsTrue();

    @Query("SELECT id AS id, name AS name, symbol AS symbol, currentPrice AS currentPrice, marketCap AS marketCap, percentChange1H AS percentChange, favourite AS favourite " +
            "FROM Stock")
    List<StockMarketDto> findAllStockMarketDtoWith1hChange();

    @Query("SELECT id AS id, name AS name, symbol AS symbol, currentPrice AS currentPrice, marketCap AS marketCap, percentChange24H AS percentChange, favourite AS favourite " +
            "FROM Stock")
    List<StockMarketDto> findAllStockMarketDtoWith24hChange();

    @Query("SELECT id AS id, name AS name, symbol AS symbol, currentPrice AS currentPrice, marketCap AS marketCap, percentChange7D AS percentChange, favourite AS favourite " +
            "FROM Stock")
    List<StockMarketDto> findAllStockMarketDtoWith7dChange();

    @Query("SELECT id AS id, name AS name, symbol AS symbol, currentPrice AS currentPrice, marketCap AS marketCap, percentChange30D AS percentChange, favourite AS favourite " +
            "FROM Stock")
    List<StockMarketDto> findAllStockMarketDtoWith30dChange();

    @Query("SELECT id AS id, name AS name, symbol AS symbol, currentPrice AS currentPrice, marketCap AS marketCap, percentChange90D AS percentChange, favourite AS favourite " +
            "FROM Stock")
    List<StockMarketDto> findAllStockMarketDtoWith90dChange();

}
