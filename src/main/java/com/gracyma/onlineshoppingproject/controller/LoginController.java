package com.gracyma.onlineshoppingproject.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "user_login";
    }

    @GetMapping("/home")
    public String home() {
        return "list_items";
    }
}
