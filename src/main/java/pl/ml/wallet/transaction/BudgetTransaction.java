package pl.ml.wallet.transaction;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class BudgetTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal transactionValue;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    public BudgetTransaction() {
    }

    public BudgetTransaction(BigDecimal transactionValue, String title, String description, TransactionType type, LocalDate date) {
        this.transactionValue = transactionValue;
        this.title = title;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTransactionValue() {
        return transactionValue;
    }

    public void setTransactionValue(BigDecimal transactionValue) {
        this.transactionValue = transactionValue;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
