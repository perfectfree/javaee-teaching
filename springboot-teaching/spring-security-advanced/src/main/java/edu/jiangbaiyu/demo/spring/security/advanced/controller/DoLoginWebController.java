package edu.jiangbaiyu.demo.spring.security.advanced.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DoLoginWebController {

    @GetMapping("/login")
    public String login(Model model) {
        // Redirect to home if already authenticated
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return "redirect:/home";
        }

        model.addAttribute("pageTitle", "现代登录");
        return "login";
    }
}