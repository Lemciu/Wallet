package pl.ml.wallet.stock;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findById(Long id);

    Optional<Stock> findBySymbol(String symbol);

    List<Stock> findByNameContainingIgnoreCase(String name);
}
