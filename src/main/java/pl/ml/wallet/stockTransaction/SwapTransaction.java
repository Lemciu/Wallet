package pl.ml.wallet.stockTransaction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SwapTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long boughtStockTransactionId;
    private Long soldStockTransactionId;

    public SwapTransaction() {
    }

    public SwapTransaction(Long boughtStockTransactionId, Long soldStockTransactionId) {
        this.boughtStockTransactionId = boughtStockTransactionId;
        this.soldStockTransactionId = soldStockTransactionId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSoldStockTransactionId() {
        return soldStockTransactionId;
    }

    public void setSoldStockTransactionId(Long soldStockId) {
        this.soldStockTransactionId = soldStockId;
    }

    public Long getBoughtStockTransactionId() {
        return boughtStockTransactionId;
    }

    public void setBoughtStockTransactionId(Long boughtStockId) {
        this.boughtStockTransactionId = boughtStockId;
    }
}
