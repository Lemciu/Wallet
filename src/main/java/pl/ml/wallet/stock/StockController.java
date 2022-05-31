package pl.ml.wallet.stock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stock.dto.StockMarketDto;

import java.util.List;

@Controller
public class StockController {
    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/accountStock")
    public String accountStock(@RequestParam String symbol, Model model) {
//        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
//        StockInfoDto stockInfoDto = stockService.getStockInfoDto(stock);
//        model.addAttribute("stock", stockInfoDto);
        return "accountStock";
    }

    @GetMapping("/marketStock")
    public String marketStock(@RequestParam String symbol,
                              @RequestParam (required = false) String range,
                              Model model) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        StockMarketDto stockMarketDto = stockService.toMarketDto(stock, range);
        model.addAttribute("stock", stockMarketDto);
        return "marketStock";
    }

    @GetMapping("buy-stock")
    public String buyStock(Model model) {
//        List<Stock> stocks = stockService.findAll();
//        model.addAttribute("stocks", stocks);
//        model.addAttribute("price", 12);
        return "transactionForm";
    }

    @GetMapping("find")
    public String findStock(@RequestParam(required = false) String findStock,
                            Model model) {
//        List<StockMarketDto> stocks = stockService.findByName(findStock);
//        model.addAttribute("stocks", stocks);
        return "market";
    }

    @GetMapping("/market")
    public String market(@RequestParam(required = false) String sort,
                         @RequestParam(required = false) String range,
                         @RequestParam(required = false) String findStock,
                         Model model) {
        //#rank/id , price, change, marketcap i favourite

        List<StockMarketDto> stocks = stockService.findAll(range);
        model.addAttribute("stocks", stocks);
        return "market";
    }

}
