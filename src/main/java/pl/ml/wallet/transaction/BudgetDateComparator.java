package pl.ml.wallet.transaction;

import java.util.Comparator;

public class BudgetDateComparator implements Comparator<BudgetTransaction> {
    @Override
    public int compare(BudgetTransaction o1, BudgetTransaction o2) {
        return -o1.getDate().compareTo(o2.getDate());
    }

}
