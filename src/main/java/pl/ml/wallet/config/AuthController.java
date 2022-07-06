package pl.ml.wallet.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginForm() {
        return "loginForm";
    }

    @PostMapping("/login")
    public String login() {
        return "redirect:/";
    }
}
