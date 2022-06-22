package pl.ml.wallet.transaction;

public enum TransactionType {
    INCOME("Income"), EXPENSE("Expense");

    private String name;

    TransactionType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
