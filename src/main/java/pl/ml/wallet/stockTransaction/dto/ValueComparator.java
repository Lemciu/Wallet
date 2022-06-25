package pl.ml.wallet.stockTransaction.dto;

import java.util.Comparator;

public class ValueComparator  implements Comparator<AccountDto> {
    @Override
    public int compare(AccountDto o1, AccountDto o2) {
        return -o1.getValue().compareTo(o2.getValue());
    }
}
