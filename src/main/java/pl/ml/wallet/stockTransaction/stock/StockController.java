package pl.ml.wallet.stockTransaction.stock;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.stockTransaction.stock.dto.StockMarketDto;
import pl.ml.wallet.stockTransaction.stock.dto.StockMarketProfileDto;
import pl.ml.wallet.transaction.BudgetTransactionService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StockController {
    private StockService stockService;
    private BudgetTransactionService budgetTransactionService;

    public StockController(StockService stockService, BudgetTransactionService budgetTransactionService) {
        this.stockService = stockService;
        this.budgetTransactionService = budgetTransactionService;
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

    @GetMapping("/market")// market/2
    public String market(@RequestParam(required = false) String sort,
                         @RequestParam(required = false) String range,
                         @RequestParam(required = false) String title,
                         @PathVariable(required = false) String page,
                         Model model) {
        model.addAttribute("title", title);
        model.addAttribute("range", range);
        List<StockMarketDto> stocks = stockService.findAll(title, range);
        model.addAttribute("stocks", stocks.stream().filter(s -> s.getId() <= 20).collect(Collectors.toList()));
        return "market";

    }

    @GetMapping("/addToFavourite") // scalić te metody
    public String addToFavourite(@RequestParam String symbol,
                                 @RequestParam String side) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(true);
        stockService.save(stock);
        return "redirect:/" + side + "?symbol=" + symbol;
    }

    @GetMapping("/deleteFromFavourite")
    public String deleteFromFavourite(@RequestParam String symbol,
                                      @RequestParam String side) {
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(false);
        stockService.save(stock);
        return "redirect:/" + side + "?symbol=" + symbol;
    }

    @GetMapping("/addToFavouriteInMarket") // scalić te metody
    public String addToFavouriteInMarket(Model model,
                                         @RequestParam String symbol,
                                         @RequestParam(required = false) String range,
                                         @RequestParam(required = false) String title) {
        System.out.println("Add:");
        System.out.println("symbol: " + symbol);
        System.out.println("range: " + range);
        System.out.println("title: " + title);
        ////////////////////////////////
        model.addAttribute("title", title);
        model.addAttribute("range", range);
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(true);
        stockService.save(stock);
        if (title == null) {
            title = "";
        }
        if (range == null) {
            range = "";
        }
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
        System.out.println("Delete:");
        System.out.println("symbol: " + symbol);
        System.out.println("range: " + range);
        System.out.println("title: " + title);
        ////////////////////////////////
        model.addAttribute("title", title);
        model.addAttribute("range", range);
        Stock stock = stockService.findBySymbol(symbol).orElseThrow();
        stock.setFavourite(false);
        stockService.save(stock);

        if (title == null) {
            title = "";
        }
        if (range == null) {
            range = "";
        }
        if (!range.equals("")) {
            return "redirect:/market?title=" + title + "&range=" + range;
        }
        else {
            return "redirect:/market?title=" + title;
        }
    }

}
