package pl.ml.wallet.currencyExchange;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;

@Service
public class CurrencyExchange {
    private CurrencyService currencyService;
    private static BigDecimal dollarValue;

    public CurrencyExchange(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public static BigDecimal getDollarValue() {
        return dollarValue;
    }

    public static void setDollarValue(BigDecimal dollarValue) {
        CurrencyExchange.dollarValue = dollarValue;
    }

    public static BigDecimal toPln(BigDecimal usdValue) {
        return usdValue.multiply(dollarValue);
    }

    public static BigDecimal toUsd(BigDecimal plnValue) {
        return plnValue.divide(dollarValue, MathContext.DECIMAL64);
    }
}
