package me.lofienjoyer.quiet.front.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping("")
    public Mono<String> welcome() {
        return Mono.just("welcome");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public Mono<String> index() {
        return Mono.just("index");
    }

    @GetMapping("/search/**")
    @PreAuthorize("isAuthenticated()")
    public Mono<String> search() {
        return Mono.just("search/search");
    }

}
