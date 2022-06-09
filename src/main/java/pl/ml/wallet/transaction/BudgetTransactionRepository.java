package pl.ml.wallet.transaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetTransactionRepository extends JpaRepository<BudgetTransaction, Long> {
}
