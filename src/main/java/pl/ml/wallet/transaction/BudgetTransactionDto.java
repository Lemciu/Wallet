package pl.ml.wallet.transaction;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface BudgetTransactionDto {
    BigDecimal getTransactionValue();

    TransactionType getType();

}
