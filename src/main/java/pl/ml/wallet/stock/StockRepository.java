package pl.ml.wallet.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.ml.wallet.stock.dto.StockMarketDto;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findById(Long id);

    Optional<Stock> findBySymbol(String symbol);

    @Query("SELECT name AS name, currentPrice AS currentPrice, percentChange24H AS percentChange FROM Stock " +
            "WHERE favourite = true")
    List<StockMarketDto> findAllByFavouriteIsTrue();

    List<Stock> findByNameContainingIgnoreCase(String name);

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
