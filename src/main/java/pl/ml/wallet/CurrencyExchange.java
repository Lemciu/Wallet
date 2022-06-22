package pl.ml.wallet;

import java.math.BigDecimal;
import java.math.MathContext;

public class CurrencyExchange {

    public static BigDecimal toPln(BigDecimal usdValue) {
        return usdValue.multiply(BigDecimal.valueOf(4.28));
    }

    public static BigDecimal toUsd(BigDecimal plnValue) {
        return plnValue.divide(BigDecimal.valueOf(4.28), MathContext.DECIMAL64);
    }
}
