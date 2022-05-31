package pl.ml.wallet.stock;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.ml.wallet.stock.api.StockDto;
import pl.ml.wallet.stock.api.StockResponseDto;
import pl.ml.wallet.stock.comparator.*;
import pl.ml.wallet.stock.dto.StockMarketDto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StockService {
    // paginacje zrobić na markecie
    private StockRepository stockRepository;
    private static final String URL = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=3a006fe7-4d68-4939-8b7b-d2442e3405bb&start=1&limit=10";

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<StockMarketDto> findAll(String range) {
        List<Stock> all = stockRepository.findAll();

//        List<StockMarketDto> test = stockRepository.findAllStockMarketDtoWith1hChange();

//        if (test == null) {
//            System.out.println("to null");
//        } else {
//            System.out.println("jednak nie");
//            System.out.println(test.size());
//        }


        if (range == null) {
            range = "1D";
        }
        switch (range) {
            case "1h":
                break;
            case "1D":
            case "1W":
            case "1M":
            case "2M":
            case "3M":
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + range);
        }

        List<StockMarketDto> collect = all.stream().map(s -> {


            return new StockMarketDto(s.getId(), s.getName(),
                    s.getSymbol(), s.getCurrentPrice(), s.getMarketCap(),
                    s.getPercentChange24H(), s.isFavourite());
        }).collect(Collectors.toList());
        return null;
    }

    public List<StockDto> getAllStocks() {
        RestTemplate restTemplate = new RestTemplate();

        StockResponseDto forObject = restTemplate.getForObject(URL, StockResponseDto.class);
        return forObject.getData();
    }

//    public List<StockMarketDto> findByName(String name) {
//        if (name == null) {
////            return findAllStocksToMarket();
//            return null;
//        } else {
//            return stockRepository.findByNameContainingIgnoreCase(name)
//                    .stream()
//                    .map(this::toMarketDto)
//                    .collect(Collectors.toList());
//        }
//    }

    public StockMarketDto toMarketDto(Stock stock, String range) {
        StockMarketDto dto = new StockMarketDto();
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

//    public List<StockMarketDto> findAllStocksToMarket() {
//        List<Stock> all = stockRepository.findAll();
//        AtomicInteger id = new AtomicInteger(1);
//
//        return all.stream().map(s ->
//                new StockMarketDto(id.getAndIncrement(), s.getName(), s.getSymbol(),
//                        s.getCurrentPrice(), s.getMarketCap(), s.getPercentChange1H(),
//                        s.getPercentChange24H(), s.getPercentChange7D(), s.isFavourite())
//        ).collect(Collectors.toList());
//    }

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
