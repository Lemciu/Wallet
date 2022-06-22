package pl.ml.wallet.stockTransaction.stock;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ml.wallet.stockTransaction.stock.api.StockDto;
import pl.ml.wallet.stockTransaction.stock.api.StockResponseDto;
import pl.ml.wallet.stockTransaction.stock.comparator.*;
import pl.ml.wallet.stockTransaction.stock.dto.StockMarketDto;
import pl.ml.wallet.stockTransaction.stock.dto.StockMarketProfileDto;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {
    // paginacje zrobić na markecie
    private StockRepository stockRepository;
    private static final String URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=3a006fe7-4d68-4939-8b7b-d2442e3405bb&start=1&limit=60";

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<StockMarketDto> findAll(String title, String range) {
        List<StockMarketDto> stocks;
        if (range == null) {
            range = "1D";
        }
        switch (range) {
            case "1h":
                stocks = stockRepository.findAllStockMarketDtoWith1hChange();
                break;
            case "1D":
                stocks = stockRepository.findAllStockMarketDtoWith24hChange();
                break;
            case "1W":
                stocks = stockRepository.findAllStockMarketDtoWith7dChange();
                break;
            case "1M":
                stocks = stockRepository.findAllStockMarketDtoWith30dChange();
                break;
            case "3M":
                stocks = stockRepository.findAllStockMarketDtoWith90dChange();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + range);
        }
        if (title != null) {
            return stocks.stream().filter(s ->
                    s.getName()
                            .toLowerCase()
                            .contains(title.toLowerCase()) ||
                                    s.getSymbol()
                            .toLowerCase()
                            .contains(title.toLowerCase())
                    )
                    .collect(Collectors.toList());

        } else {
            return stocks;
        }
    }

    public StockMarketProfileDto toMarketDto(Stock stock, String range) {
        StockMarketProfileDto dto = new StockMarketProfileDto();
        if (range == null) {
            range = "1D";
        }
        switch (range) {
            case "1h":
                dto.setPercentChange(stock.getPercentChange1H());
                break;
            case "1D":
                dto.setPercentChange(stock.getPercentChange24H());
                break;
            case "1W":
                dto.setPercentChange(stock.getPercentChange7D());
                break;
            case "1M":
                dto.setPercentChange(stock.getPercentChange30D());
                break;
            case "2M":
                dto.setPercentChange(stock.getPercentChange60D());
                break;
            case "3M":
                dto.setPercentChange(stock.getPercentChange90D());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + range);
        }
        dto.setId(stock.getId());
        dto.setName(stock.getName());
        dto.setSymbol(stock.getSymbol());
        dto.setCurrentPrice(stock.getCurrentPrice());
        dto.setMarketCap(stock.getMarketCap());
        dto.setMarketCapDominance(stock.getMarketCapDominance());
        dto.setFullyDilutedMarketCap(stock.getFullyDilutedMarketCap());
        dto.setVolume24H(stock.getVolume24H());
        dto.setVolumeChange24H(stock.getVolumeChange24H());
        dto.setCirculatingSupply(stock.getCirculatingSupply());
        dto.setTotalSupply(stock.getTotalSupply());
        dto.setMaxSupply(stock.getMaxSupply());
        dto.setFavourite(stock.isFavourite());
        return dto;
    }

    public void sort(String sort, List<StockMarketProfileDto> stocks) {
        switch (sort) {
            case "crypto":
                stocks.sort(new StockNameComparator());
                break;
            case "crypto-desc":
                stocks.sort(new StockNameComparator().reversed());
                break;
            case "price":
                stocks.sort(new StockPriceComparator());
                break;
            case "price-desc":
                stocks.sort(new StockPriceComparator().reversed());
                break;
            case "1h":
                stocks.sort(new Stock1hComparator());
                break;
            case "1h-desc":
                stocks.sort(new Stock1hComparator().reversed());
                break;
            case "24h":
                stocks.sort(new Stock24hComparator());
                break;
            case "24h-desc":
                stocks.sort(new Stock24hComparator().reversed());
                break;
            case "7d":
                stocks.sort(new Stock7dComparator());
                break;
            case "7d-desc":
                stocks.sort(new Stock7dComparator().reversed());
                break;
            case "marketcap":
                stocks.sort(new StockMarketCapComparator());
                break;
            case "marketcap-desc":
                stocks.sort(new StockMarketCapComparator().reversed());
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + sort);
        }
    }

    public void init() {
        RestTemplate restTemplate = new RestTemplate();
        List<Stock> all = stockRepository.findAll();

        StockResponseDto forObject = restTemplate.getForObject(URL, StockResponseDto.class);
        List<StockDto> data = forObject.getData();
        System.out.println(data.get(0).getQuote().getUsd().getPrice() + " tajemnicza cena");
        data.stream().forEach(s -> {
            String name = s.getName();
            String symbol = s.getSymbol();
            BigDecimal maxSupply = s.getMaxSupply();
            BigDecimal totalSupply = s.getTotalSupply();
            BigDecimal circulatingSupply = s.getCirculatingSupply();
            BigDecimal price = s.getQuote().getUsd().getPrice();
            double percentChange1H = s.getQuote().getUsd().getPercentChange1H();
            double percentChange24H = s.getQuote().getUsd().getPercentChange24H();
            double percentChange7D = s.getQuote().getUsd().getPercentChange7D();
            double percentChange30D = s.getQuote().getUsd().getPercentChange30D();
            double percentChange60D = s.getQuote().getUsd().getPercentChange60D();
            double percentChange90D = s.getQuote().getUsd().getPercentChange90D();
            BigDecimal marketCap = s.getQuote().getUsd().getMarketCap();
            double marketCapDominance = s.getQuote().getUsd().getMarketCapDominance();
            BigDecimal fullyDilutedMarketCap = s.getQuote().getUsd().getFullyDilutedMarketCap();
            BigDecimal volume24H = s.getQuote().getUsd().getVolume24H();
            double volumeChange24H = s.getQuote().getUsd().getVolumeChange24H();

            all.stream().filter(s1 -> s1.getSymbol().equals(symbol)).forEach(s2 -> {
                s2.setName(name);
                s2.setMaxSupply(maxSupply);
                s2.setTotalSupply(totalSupply);
                s2.setCirculatingSupply(circulatingSupply);
                s2.setPercentChange1H(percentChange1H);
                s2.setPercentChange24H(percentChange24H);
                s2.setPercentChange7D(percentChange7D);
                s2.setPercentChange30D(percentChange30D);
                s2.setPercentChange60D(percentChange60D);
                s2.setPercentChange90D(percentChange90D);
                s2.setCurrentPrice(price);
                s2.setMarketCap(marketCap);
                s2.setMarketCapDominance(marketCapDominance);
                s2.setFullyDilutedMarketCap(fullyDilutedMarketCap);
                s2.setVolume24H(volume24H);
                s2.setVolumeChange24H(volumeChange24H);
                save(s2);
            });
        });
    }

    public void save(Stock stock) {
        stockRepository.save(stock);
    }

    public Optional<Stock> findBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }

    public List<StockMarketDto> findFavouriteStocks() {
        return stockRepository.findAllByFavouriteIsTrue();
    }

    public Stock findById(long id) {
        return stockRepository.findById(id).orElseThrow();
    }

    public List<StockMarketDto> findAllStockNames() {
        return stockRepository.findAllStocksName();
    }

}
