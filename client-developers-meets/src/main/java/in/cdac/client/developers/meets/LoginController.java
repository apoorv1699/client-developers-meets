package in.cdac.client.developers.meets;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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

    /** Client welcome page. */
    @GetMapping("/welcome/client")
    public RedirectView clientWelcome() {
        return new RedirectView("/welcome-client.html");
    }

    /** Developer welcome page. */
    @GetMapping("/welcome/developer")
    public RedirectView developerWelcome() {
        return new RedirectView("/welcome-developer.html");
    }

    /** Handle client login. */
    @PostMapping("/login/client")
    public RedirectView handleClientLogin() {
        // TODO: Add authentication logic here
        return new RedirectView("/welcome/client");
    }

    /** Handle developer login. */
    @PostMapping("/login/developer")
    public RedirectView handleDeveloperLogin() {
        // TODO: Add authentication logic here
        return new RedirectView("/welcome/developer");
    }

    /** Logout handler. */
    @GetMapping("/logout")
    public RedirectView logout() {
        // TODO: Add session invalidation logic here
        return new RedirectView("/");
    }
}
