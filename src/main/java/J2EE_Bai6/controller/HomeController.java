package J2EE_Bai6.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("homeRestController")
public class HomeController {
    @GetMapping("/home")
    public String home(Principal principal) {
        return "Welcome to the home page, " + principal.getName() + "!";
    }
}