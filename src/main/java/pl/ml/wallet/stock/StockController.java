package pl.ml.wallet.stock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stock.dto.StockInfoDto;
import pl.ml.wallet.stock.dto.StockMarketDto;

import java.util.List;

@Controller
public class StockController {
    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/stock")
    public String stockInfo(@RequestParam String symbol, Model model) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        StockInfoDto stockInfoDto = stockService.getStockInfoDto(stock);
        model.addAttribute("stock", stockInfoDto);
        return "stock";
    }

    @GetMapping("buy-stock")
    public String buyStock(Model model) {
        List<Stock> stocks = stockService.findAll();
        model.addAttribute("stocks", stocks);
        model.addAttribute("price", 12);
        return "transactionForm";
    }

    @GetMapping("find")
    public String findStock(@RequestParam(required = false) String findStock,
                            Model model) {
        List<StockMarketDto> stocks = stockService.findByName(findStock);
        model.addAttribute("stocks", stocks);
        return "market";
    }

    @GetMapping("/market")
    public String market(@RequestParam(required = false) String sort,
                         @RequestParam(required = false) String findStock,
                         Model model) {
        stockService.init();
//        if (findStock != null) {
//            List<StockMarketDto> byName = stockService.findByName(findStock);
//        }
//        List<StockMarketDto> stocks = stockService.findAllStocksToMarket();
        List<StockMarketDto> stocks = stockService.findByName(findStock);
        if (sort == null) {

        } else {
            stockService.sort(sort, stocks);

        }
        model.addAttribute("stocks", stocks);
        return "market";
    }

    @GetMapping("addTofavourite") // scalić to do jednej metody
    public String likeStock(@RequestParam String symbol) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(true);
        stockService.save(stock);
        return "redirect:/market";
    }

    @GetMapping("deleteFromfavourite")
    public String unlikeStock(@RequestParam String symbol, @RequestParam(required = false) String sort) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(false);
        stockService.save(stock);
        if (sort == null) {
            System.out.println("sort to null");
            return "redirect:/market";
        } else {
            System.out.println("sort to nie null");

            return "redirect:/market?sort=" + sort;
        }
    }

}
