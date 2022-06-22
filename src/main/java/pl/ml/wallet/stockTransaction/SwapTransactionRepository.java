package pl.ml.wallet.stockTransaction;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SwapTransactionRepository  extends JpaRepository<SwapTransaction, Long> {
}
