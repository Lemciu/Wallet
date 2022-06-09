package pl.ml.wallet.stockTransaction;

import org.springframework.format.annotation.DateTimeFormat;
import pl.ml.wallet.transaction.TransactionType;
import pl.ml.wallet.stockTransaction.stock.Stock;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class StockTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @ManyToOne
    private Stock stock;
    private BigDecimal amount;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private StockTransactionType type;

    public StockTransaction(LocalDate date, Stock stock, BigDecimal amount, BigDecimal price, StockTransactionType type) {
        this.date = date;
        this.stock = stock;
        this.amount = amount;
        this.price = price;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal buyPrice) {
        this.price = buyPrice;
    }

    public BigDecimal getBuyValue() {
        return amount.multiply(price);
    }

    public BigDecimal getCurrentValue() {
        return amount.multiply(stock.getCurrentPrice());
    }

    public StockTransactionType getType() {
        return type;
    }

    public void setType(StockTransactionType type) {
        this.type = type;
    }
}

