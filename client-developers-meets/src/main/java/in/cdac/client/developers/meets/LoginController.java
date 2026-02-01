package in.cdac.client.developers.meets;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.view.RedirectView;

/** Routes for login and signup pages (redirect to static HTML). */
@Controller
public class LoginController {

    /** Client login page. */
    @GetMapping("/login/client")
    public RedirectView clientLogin() {
        return new RedirectView("/login-client.html");
    }

    /** Developer login page. */
    @GetMapping("/login/developer")
    public RedirectView developerLogin() {
        return new RedirectView("/login-developer.html");
    }

    /** Client signup page. */
    @GetMapping("/signup/client")
    public RedirectView clientSignup() {
        return new RedirectView("/signup-client.html");
    }

    /** Developer signup page. */
    @GetMapping("/signup/developer")
    public RedirectView developerSignup() {
        return new RedirectView("/signup-developer.html");
    }
}
