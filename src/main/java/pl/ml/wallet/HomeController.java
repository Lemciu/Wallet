package pl.ml.wallet;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.notification.Notification;
import pl.ml.wallet.notification.NotificationService;
import pl.ml.wallet.stock.Stock;
import pl.ml.wallet.stock.StockService;
import pl.ml.wallet.transaction.TransactionService;
import pl.ml.wallet.transaction.dto.TransactionOwnedDto;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class HomeController {
    private NotificationService notificationService;
    private StockService stockService;
    private TransactionService transactionService;

    public HomeController(NotificationService notificationService, StockService stockService, TransactionService transactionService) {
        this.notificationService = notificationService;
        this.stockService = stockService;
        this.transactionService = transactionService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Stock> all = stockService.findAll();
        all.forEach(System.out::println);
//        BigDecimal currentValue = transactionService.getCurrentSaldo();
//        double percentageProfit = transactionService.getPercentageProfitSaldo();
//        model.addAttribute("currentValue", currentValue);
//        model.addAttribute("percentageProfit", percentageProfit);
        List<Notification> notifications = notificationService.findAll();
        model.addAttribute("notifications", notifications);
        return "dashboard";
    }

    @GetMapping("/investments")
    public String investments(Model model) {
        BigDecimal currentValue = transactionService.getCurrentSaldo();
        double percentageProfit = transactionService.getPercentageProfitSaldo();
        BigDecimal profit = transactionService.getProfitSaldo();
        List<TransactionOwnedDto> stocks = transactionService.getAllOwnedTransactions();
        model.addAttribute("stocks", stocks);
        model.addAttribute("currentValue", currentValue);
        model.addAttribute("percentageProfit", percentageProfit);
        model.addAttribute("profit", profit);
        return "investments";
    }

    @GetMapping("/savings")
    public String savings() {
        return "savings";
    }

    @GetMapping("/add-notification")
    public String addNotification(Model model) {
        Notification newNotification = new Notification("", "", null, false);
        model.addAttribute("notification", newNotification);
        return "notification";
    }

    @GetMapping("/edit-notification")
    public String editNotification(@RequestParam Long id, Model model) {
        Notification notification = notificationService.findById(id).orElseThrow();
        model.addAttribute("notification", notification);
        return "notification";
    }

    @PostMapping("/save-notification")
    public String saveNotification(Notification notification) {
        notificationService.save(notification);
        return "redirect:/notifications";
    }

    @GetMapping("/notifications")
    public String notifications(@RequestParam(required = false) boolean finished, Model model) {
        List<Notification> notifications = notificationService.findAllByFinished(finished);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd");
        model.addAttribute("formatter", formatter);
        model.addAttribute("notifications", notifications);

        return "notifications";
    }

    @PostMapping("/completed")
    public String completed(@RequestParam Long id) {
        Notification notification = notificationService.findById(id).orElseThrow();
        notificationService.setFinishedNotification(notification);
        notificationService.save(notification);
        return "redirect:/notifications";
    }

    @GetMapping("get-price")
    public String getPrice(Model model) {
        model.addAttribute("price", 15);
        return "transactionForm";
    }

}
