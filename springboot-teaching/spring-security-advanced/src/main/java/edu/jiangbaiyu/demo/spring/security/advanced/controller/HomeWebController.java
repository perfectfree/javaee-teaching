package edu.jiangbaiyu.demo.spring.security.advanced.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeWebController {

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        model.addAttribute("username", authentication.getName());
        model.addAttribute("pageTitle", "控制台");
        System.out.println("Authorities: " + authentication.getAuthorities());
        return "home";
    }

    @GetMapping("/")
    public String root() {
        return "redirect:/home";
    }
}