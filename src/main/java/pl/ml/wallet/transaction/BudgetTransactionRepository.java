package pl.ml.wallet.transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BudgetTransactionRepository extends JpaRepository<BudgetTransaction, Long> {

    @Query("SELECT transactionValue as transactionValue, type AS type FROM BudgetTransaction WHERE date >= ?#{[0]}")
    List<BudgetTransactionDto> findAllToGetProfit(LocalDate date);

    List<BudgetTransaction> findAllByType(TransactionType type);

}
