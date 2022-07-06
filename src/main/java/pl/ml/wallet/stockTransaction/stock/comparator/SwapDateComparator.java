package pl.ml.wallet.stockTransaction.stock.comparator;

import pl.ml.wallet.stockTransaction.stock.TransactionDto;

import java.util.Comparator;

public class SwapDateComparator implements Comparator<TransactionDto> {
    @Override
    public int compare(TransactionDto o1, TransactionDto o2) {
        return -o1.getDate().compareTo(o2.getDate());
    }
}
