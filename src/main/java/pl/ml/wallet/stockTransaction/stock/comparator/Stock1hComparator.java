package pl.ml.wallet.stockTransaction.stock.comparator;

import pl.ml.wallet.stockTransaction.stock.dto.StockMarketProfileDto;

import java.util.Comparator;

public class Stock1hComparator implements Comparator<StockMarketProfileDto> {
    @Override
    public int compare(StockMarketProfileDto o1, StockMarketProfileDto o2) {
        return o1.getPercentChange1H().compareTo(o2.getPercentChange1H());
    }
}
