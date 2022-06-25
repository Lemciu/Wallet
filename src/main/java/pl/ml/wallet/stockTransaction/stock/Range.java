package pl.ml.wallet.stockTransaction.stock;

public enum Range {
    ONE_HOUR("1h"),
    ONE_DAY("1D"),
    ONE_WEEK("1W"),
    ONE_MONTH("1M"),
    TWO_MONTH("2M"),
    THREE_MONTH("3M");

    private String name;

    Range(String name) {
        this.name = name;
    }
}
