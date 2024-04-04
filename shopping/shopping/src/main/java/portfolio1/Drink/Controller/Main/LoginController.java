package portfolio1.Drink.Controller.Main;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import portfolio1.Drink.Service.JoinService;

@Controller
@RequiredArgsConstructor
public class LoginController
{
    private final JoinService joinService;

    @GetMapping("/home")
    public String home()
    {
        return "Login/home";
    }

    @GetMapping("/login")
    public String login()
    {
        return "Login/login";
    }

    @GetMapping("/logout")
    public String logout()
    {
        return "/logout";
    }

    @GetMapping("/login/fail")
    public String login_fail()
    {
        return "Login/fail";
    }



}
