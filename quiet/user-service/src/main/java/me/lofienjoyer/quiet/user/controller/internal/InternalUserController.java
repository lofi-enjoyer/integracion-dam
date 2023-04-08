package me.lofienjoyer.quiet.user.controller.internal;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import me.lofienjoyer.quiet.user.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public Mono<UserInfo> getCurrentUser(Authentication authentication) {
        return userService.getUserByEmail(authentication.getName());
    }

    @GetMapping("/email")
    public Mono<UserInfo> getUserByEmail(@RequestParam("email") String email) {
        return userService.getUserByEmail(email);
    }

    @GetMapping("/id")
    public Mono<UserInfo> getUserById(@RequestParam("id") long id) {
        return userService.getUserById(id);
    }

}
