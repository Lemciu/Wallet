package pl.ml.wallet.transaction.dto;

import java.math.BigDecimal;

public class TransactionDto {

    private String name;
    private String symbol;
    private BigDecimal amount;
    private BigDecimal value;
    private Double percentChange;
    private Double currentPrice;
    // lista tych transakcji ?
//    List<Transaction>
//    może na razie bez historii transakcji
//    Name, symbol,currentPrice, amount,value, percentChange, List<Transaction>
//    transaction -> date,amount,value,from to?

//    może  tez byc rownie tylko BTC -> ADA 0.0923 BTC i data
//    i dopiero w detalach więcej info wyświetlać
}
