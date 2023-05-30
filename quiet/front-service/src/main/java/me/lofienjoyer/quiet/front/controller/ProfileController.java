package me.lofienjoyer.quiet.front.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

/**
 * Handles profile-related requests
 */
@Controller
@RequestMapping("/profile")
public class ProfileController {

    /**
     * Specific profile page
     * @param username Username to access
     */
    @GetMapping("/{username}")
    public Mono<String> profile(@PathVariable("username") String username) {
        return Mono.just("profile/profile");
    }

    /**
     * Edit current user's profile
     */
    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public Mono<String> editProfile() {
        return Mono.just("profile/editProfile");
    }

}
