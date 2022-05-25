package pl.ml.wallet.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.ml.wallet.stock.Stock;
import pl.ml.wallet.transaction.dto.TransactionDto;
import pl.ml.wallet.transaction.dto.TransactionOwnedDto;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    @Query("SELECT t.amount AS amount, t.buyPrice AS buyPrice, s.currentPrice AS currentPrice " +
            "FROM Transaction t LEFT JOIN Stock s ON t.stock.id = s.id")
    List<TransactionDto> findAllToSaldo();

    List<Transaction> findAllByStock_Id(Long id);

    List<Transaction> findAllByStock(Stock stock);
}
