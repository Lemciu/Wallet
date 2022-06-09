package pl.ml.wallet.stockTransaction.stock.comparator;

import pl.ml.wallet.stockTransaction.stock.dto.StockMarketProfileDto;

import java.util.Comparator;

public class Stock24hComparator implements Comparator<StockMarketProfileDto> {
    @Override
    public int compare(StockMarketProfileDto o1, StockMarketProfileDto o2) {
        return o1.getPercentChange24H().compareTo(o2.getPercentChange24H());
    }
}
