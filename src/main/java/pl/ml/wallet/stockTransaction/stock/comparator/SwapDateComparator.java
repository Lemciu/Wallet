package pl.ml.wallet.stockTransaction.stock.comparator;

import pl.ml.wallet.stockTransaction.stock.SwapTransactionDto;

import java.util.Comparator;

public class SwapDateComparator implements Comparator<SwapTransactionDto> {
    @Override
    public int compare(SwapTransactionDto o1, SwapTransactionDto o2) {
        return -o1.getDate().compareTo(o2.getDate());
    }
}
