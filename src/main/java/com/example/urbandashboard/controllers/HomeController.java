package com.example.urbandashboard.controllers;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;


@Controller
public class HomeController {
    @GetMapping({"", "/index"})
    public String getHomePage(){
        return  "index";
    }
    @GetMapping("/contact")
    public String getContactPage(){
        return "contact";
    }
    @GetMapping("/privacy")
    public String getPrivacyPage(){
        return "privacy";
    }
    @GetMapping("/test")
    public String testPage() {
        return "test";
    }
}
