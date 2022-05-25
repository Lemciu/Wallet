package pl.ml.wallet.comparator;

import pl.ml.wallet.transaction.dto.TransactionOwnedDto;

import java.util.Comparator;

public class StockOwnedValue implements Comparator<TransactionOwnedDto> {
    @Override
    public int compare(TransactionOwnedDto t1, TransactionOwnedDto t2) {
        return -t1.getCurrentValue().compareTo(t2.getCurrentValue());
    }
}
