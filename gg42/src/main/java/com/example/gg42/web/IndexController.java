package com.example.gg42.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String index(@CookieValue(value = "userName", required = false) String userName, Model model) {
        if (userName != null) {
            model.addAttribute("userName", userName);
        }
        return "index";
    }
}