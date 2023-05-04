package me.lofienjoyer.quiet.front.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Controller
public class AuthController {

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public Mono<String> login() {
        return Mono.just("auth/login");
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public Mono<String> register() {
        return Mono.just("auth/register");
    }

    @GetMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public Mono<String> logout(ServerWebExchange exchange) {
        ResponseCookie cookie = ResponseCookie.from("token", "")
                .path("/")
                .maxAge(0)
                .build();

        exchange.getResponse().addCookie(cookie);

        return Mono.just("redirect:/login");
    }

}
