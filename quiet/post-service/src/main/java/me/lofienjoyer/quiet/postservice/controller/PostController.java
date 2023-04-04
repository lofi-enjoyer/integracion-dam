package me.lofienjoyer.quiet.postservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @GetMapping("/check")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<String> check(Authentication authentication) {
        return Mono.just("Perfe, tu email es " + authentication.getPrincipal().toString() + " :)");
    }

}
