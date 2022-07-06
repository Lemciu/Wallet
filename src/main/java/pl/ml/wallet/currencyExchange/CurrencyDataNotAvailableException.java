package pl.ml.wallet.currencyExchange;

public class CurrencyDataNotAvailableException extends RuntimeException {

    public CurrencyDataNotAvailableException() {
        super("Could not download currency rates");
    }
}
