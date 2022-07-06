package pl.ml.wallet.stockTransaction.stock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stockTransaction.stock.dto.StockMarketDto;
import pl.ml.wallet.stockTransaction.stock.dto.StockMarketProfileDto;

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

    @GetMapping("/market")
    public String market(@RequestParam(required = false) String range,
                         @RequestParam(required = false) String title,
                         Model model) {
        model.addAttribute("title", title);
        model.addAttribute("range", range);
        List<StockMarketDto> stocks = stockService.findAll(title, range);
        model.addAttribute("stocks", stockService.findFirst20Stocks(stocks));
        return "market";

    }

    @GetMapping("/addToFavourite")
    public String addToFavourite(@RequestParam String symbol,
                                 @RequestParam String side) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        System.out.println("side: " + side);
        stock.setFavourite(true);
        stockService.save(stock);
        return "redirect:/" + side + "?symbol=" + symbol;
    }

    @GetMapping("/deleteFromFavourite")
    public String deleteFromFavourite(@RequestParam String symbol,
                                      @RequestParam String side) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(false);
        System.out.println("side: " + side);
        stockService.save(stock);
        return "redirect:/" + side + "?symbol=" + symbol;
    }

    @GetMapping("/addToFavouriteInMarket")
    public String addToFavouriteInMarket(Model model,
                                         @RequestParam String symbol,
                                         @RequestParam(required = false) String range,
                                         @RequestParam(required = false) String title) {
        model.addAttribute("title", stockService.getTitle(title));
        model.addAttribute("range", stockService.getRange(range));
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(true);
        stockService.save(stock);

        if (!range.equals("")) {
            return "redirect:/market?title=" + title + "&range=" + range;
        }
        else {
            return "redirect:/market?title=" + title;
        }

    }

    @GetMapping("/deleteFromFavouriteInMarket")
    public String deleteFromFavouriteInMarket(Model model,
                                              @RequestParam String symbol,
                                              @RequestParam(required = false) String range,
                                              @RequestParam(required = false) String title) {
        model.addAttribute("title", stockService.getTitle(title));
        model.addAttribute("range", stockService.getRange(range));
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(false);
        stockService.save(stock);

        if (!range.equals("")) {
            return "redirect:/market?title=" + title + "&range=" + range;
        }
        else {
            return "redirect:/market?title=" + title;
        }
    }

}
