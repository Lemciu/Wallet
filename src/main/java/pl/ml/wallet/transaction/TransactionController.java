package pl.ml.wallet.transaction;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.ml.wallet.transaction.dto.TransactionOwnedDto;

import java.util.List;

@Controller
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/accounts")
    public String accounts(@RequestParam (required = false) String range,
            Model model) {
        model.addAttribute("accounts",
                transactionService.toAccountDto(transactionService.findAllOwnedAccounts(range)));
        return "accounts";
    }

    @GetMapping("/accountStock")
    public String accountStock(@RequestParam String symbol,
                                Model model) {
        System.out.println(symbol);
        return "accountStock";
    }
}
