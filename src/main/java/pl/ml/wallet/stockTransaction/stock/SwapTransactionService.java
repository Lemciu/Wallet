package pl.ml.wallet.stockTransaction.stock;

import org.springframework.stereotype.Service;
import pl.ml.wallet.stockTransaction.SwapTransaction;
import pl.ml.wallet.stockTransaction.SwapTransactionRepository;

import java.util.List;

@Service
public class SwapTransactionService {
    private SwapTransactionRepository swapTransactionRepository;

    public SwapTransactionService(SwapTransactionRepository swapTransactionRepository) {
        this.swapTransactionRepository = swapTransactionRepository;
    }

    public List<SwapTransaction> findAll() {
        return swapTransactionRepository.findAll();
    }

    public void add(Long boughtStockId, Long soldStockId) {
        swapTransactionRepository.save(new SwapTransaction(boughtStockId, soldStockId));
    }

}
