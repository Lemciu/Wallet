package pl.ml.wallet.transaction;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.ml.wallet.transaction.dto.TransactionOwnedDto;

import java.util.List;

@Controller
public class TransactionController {
    private TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/accounts")
    public String accounts() {
        List<TransactionOwnedDto> accounts = transactionService.findAllOwnedAccounts();
        System.out.println(accounts.size());
        return "accounts";
    }
}
