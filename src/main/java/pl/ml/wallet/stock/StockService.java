package pl.ml.wallet.stock;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ml.wallet.stock.api.StockDto;
import pl.ml.wallet.stock.api.StockResponseDto;
import pl.ml.wallet.stock.comparator.*;
import pl.ml.wallet.stock.dto.StockInfoDto;
import pl.ml.wallet.stock.dto.StockMarketDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class StockService {
    // paginacje zrobić na markecie
    private StockRepository stockRepository;
    private static final String URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=3a006fe7-4d68-4939-8b7b-d2442e3405bb&start=1&limit=10";

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> findAll() {
        return stockRepository.findAll();
    }

    public List<StockDto> getAllStocks() {
        RestTemplate restTemplate = new RestTemplate();

        StockResponseDto forObject = restTemplate.getForObject(URL, StockResponseDto.class);
        return forObject.getData();
    }

    public List<StockMarketDto> findByName(String name) {
        if (name == null) {
            return findAllStocksToMarket();
        } else {
            return stockRepository.findByNameContainingIgnoreCase(name)
                    .stream()
                    .map(this::toMarketDto)
                    .collect(Collectors.toList());
        }
    }

    private StockMarketDto toMarketDto(Stock stock) {
        StockMarketDto dto = new StockMarketDto();
        dto.setId(stock.getId());
        dto.setName(stock.getName());
        dto.setSymbol(stock.getSymbol());
        dto.setCurrentPrice(stock.getCurrentPrice());
        dto.setMarketCap(stock.getMarketCap());
        dto.setPercentChange1H(stock.getPercentChange1H());
        dto.setPercentChange24H(stock.getPercentChange24H());
        dto.setPercentChange7D(stock.getPercentChange7D());
        dto.setFavourite(stock.isFavourite());
        return dto;
    }

    private StockInfoDto toInfoDto(Stock stock) {
        StockInfoDto dto = new StockInfoDto();

        dto.setName(stock.getName());
        dto.setSymbol(stock.getSymbol());
        dto.setMarketCapDominance(stock.getMarketCapDominance());
        dto.setFullyDilutedMarketCap(stock.getFullyDilutedMarketCap());
        dto.setVolume24H(stock.getVolume24H());
        dto.setVolumeChange24H(stock.getVolumeChange24H());
        dto.setMaxSupply(stock.getMaxSupply());
        dto.setTotalSupply(stock.getTotalSupply());
        dto.setCirculatingSupply(stock.getCirculatingSupply());
        dto.setCurrentPrice(stock.getCurrentPrice());
        dto.setMarketCap(stock.getMarketCap());
        dto.setPercentChange24H(stock.getPercentChange24H());
        dto.setFavourite(stock.isFavourite());
        return dto;
    }

    public void sort(String sort, List<StockMarketDto> stocks) {
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
//                throw new IllegalStateException("Unexpected value: " + sort);
        }
    }

    public void init() {
        RestTemplate restTemplate = new RestTemplate();
        List<Stock> all = stockRepository.findAll();

        StockResponseDto forObject = restTemplate.getForObject(URL, StockResponseDto.class);
        List<StockDto> data = forObject.getData();
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
                stockRepository.save(s2);
            });

        });
    }

    public StockInfoDto getStockInfoDto(Stock stock) {
        return new StockInfoDto(stock.getId(), stock.getName(), stock.getSymbol(), stock.getCurrentPrice(), stock.getMarketCap(),
                stock.getMarketCapDominance(), stock.getFullyDilutedMarketCap(), stock.getPercentChange24H(), stock.getVolume24H(),
                stock.getVolumeChange24H(), stock.isFavourite(), stock.getMaxSupply(), stock.getTotalSupply(),
                stock.getCirculatingSupply());
    }

    public List<StockMarketDto> findAllStocksToMarket() {
        List<Stock> all = stockRepository.findAll();
        AtomicInteger id = new AtomicInteger(1);

        return all.stream().map(s ->
                new StockMarketDto(id.getAndIncrement(), s.getName(), s.getSymbol(),
                        s.getCurrentPrice(), s.getMarketCap(), s.getPercentChange1H(),
                        s.getPercentChange24H(), s.getPercentChange7D(), s.isFavourite())
        ).collect(Collectors.toList());
    }

    public void save(Stock stock) {
        stockRepository.save(stock);
    }

    public Optional<Stock> findById(Long id) {
        return stockRepository.findById(id);
    }

    public Optional<Stock> findBySymbol(String symbol) {
        return stockRepository.findBySymbol(symbol);
    }

}
