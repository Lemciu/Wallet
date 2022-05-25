package pl.ml.wallet.stock.comparator;

import pl.ml.wallet.stock.dto.StockMarketDto;

import java.util.Comparator;

public class StockMarketCapComparator implements Comparator<StockMarketDto> {
    @Override
    public int compare(StockMarketDto o1, StockMarketDto o2) {
        return o1.getMarketCap().compareTo(o2.getMarketCap());
    }
}
