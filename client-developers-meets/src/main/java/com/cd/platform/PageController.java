package com.cd.platform;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String landing() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/client/home")
    public String clientHome() {
        return "client-home";
    }

    @GetMapping("/client/developers")
    public String clientDevelopers() {
        return "client-developers";
    }

    @GetMapping("/client/developer")
    public String clientDeveloperProfile() {
        return "client-developer";
    }

    @GetMapping("/client/messages")
    public String clientMessages() {
        return "client-messages";
    }

    @GetMapping("/developer/home")
    public String developerHome() {
        return "developer-home";
    }

    @GetMapping("/developer/profile")
    public String developerProfile() {
        return "developer-profile";
    }

    @GetMapping("/developer/portfolio")
    public String developerPortfolio() {
        return "developer-portfolio";
    }

    @GetMapping("/developer/messages")
    public String developerMessages() {
        return "developer-messages";
    }
}
