package pl.ml.wallet.stockTransaction.stock.comparator;

import pl.ml.wallet.stockTransaction.stock.dto.StockMarketProfileDto;

import java.util.Comparator;

public class StockMarketCapComparator implements Comparator<StockMarketProfileDto> {
    @Override
    public int compare(StockMarketProfileDto o1, StockMarketProfileDto o2) {
        return o1.getMarketCap().compareTo(o2.getMarketCap());
    }
}
