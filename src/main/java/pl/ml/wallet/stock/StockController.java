package pl.ml.wallet.stock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stock.dto.StockMarketDto;
import pl.ml.wallet.stock.dto.StockMarketProfileDto;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StockController {
    private StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping("/marketStock")
    public String marketStock(@RequestParam String symbol,
                              @RequestParam (required = false) String range,
                              Model model) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        StockMarketProfileDto stockMarketProfileDto = stockService.toMarketDto(stock, range);
        model.addAttribute("stock", stockMarketProfileDto);
        return "marketStock";
    }

    @GetMapping("buy-stock")
    public String buyStock(Model model) {
        return "transactionForm";
    }

    @GetMapping("find")
    public String findStock(@RequestParam(required = false) String findStock,
                            Model model) {

        return "market";
    }

    @GetMapping("/market")// market/2
    public String market(@RequestParam(required = false) String sort,
                         @RequestParam(required = false) String range,
                         @RequestParam(required = false) String title,
                         @PathVariable(required = false) String page,
                         Model model) {
        model.addAttribute("title", title);
        List<StockMarketDto> stocks = stockService.findAll(title, range);
//        stocks.stream().filter(s -> s.getId() <= 20).collect(Collectors.toList());
        model.addAttribute("stocks", stocks.stream().filter(s -> s.getId() <= 20).collect(Collectors.toList()));
        System.out.println("Range: " + range);
        System.out.println("Title: " + title);
        System.out.println("Page: " + page);
        return "market";

    }

    @GetMapping("/addToFavourite") // scalić te metody
    public String addToFavourite(@RequestParam String symbol) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(true);
        stockService.save(stock);
        return "redirect:/marketStock?symbol=" + symbol;
    }

    @GetMapping("/deleteFromFavourite")
    public String deleteFromFavourite(@RequestParam String symbol) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(false);
        stockService.save(stock);
        return "redirect:/marketStock?symbol=" + symbol;
    }

}
