package pl.ml.wallet.stock;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.ml.wallet.stock.dto.StockMarketDto;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findById(Long id);

    Optional<Stock> findBySymbol(String symbol);

    List<Stock> findByNameContainingIgnoreCase(String name);

//zamiast zwykłego List<Stock> zrobić odrazu za pomocą zapytania odpowiedni obiekt?

//            this.id = id;
//        this.name = name;
//        this.symbol = symbol;
//        this.currentPrice = currentPrice;
//        this.marketCap = marketCap;
//        this.percentChange = percentChange;
//        this.favourite = favourite;

//    @Query("SELECT id AS id, name AS name, symbol AS symbol, currentPrice AS currentPrice, marketCap AS marketCap, percentChange1H AS percentChange, favourite AS favourite " +
//            "FROM Stock")
//    List<StockMarketDto> findAllStockMarketDtoWith1hChange();

}
