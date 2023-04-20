package me.lofienjoyer.quiet.front.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("")
    public String index(Authentication authentication) {
        if (authentication == null)
            return "redirect:http://localhost:8080/login";
        return "index";
    }

}
