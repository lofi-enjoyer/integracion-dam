package me.lofienjoyer.quiet.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.auth.dto.CreateUserDto;
import me.lofienjoyer.quiet.auth.dto.GetTokenDto;
import me.lofienjoyer.quiet.auth.model.User;
import me.lofienjoyer.quiet.auth.service.AuthService;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authManager;

    @PostMapping("/register")
    public User createUser(@RequestBody CreateUserDto dto) {
        return authService.saveUser(dto);
    }

    @GetMapping("/token")
    public void getToken(@RequestParam("email") String email, @RequestParam("password") String password, HttpServletResponse response) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        if (authentication.isAuthenticated()) {
            String token = authService.generateToken(email);
            Cookie cookie = new Cookie("token", token);
            cookie.setPath("/");
            cookie.setHttpOnly(false);
            cookie.setMaxAge(24 * 60 * 60 * 1000);
            // TODO: Set expiration for the cookie

            response.addCookie(cookie);
        } else {
            throw new UsernameNotFoundException("User " + email + " not found");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "The token is valid.";
    }

    @GetMapping("/test")
    public String amILoggedIn(@CookieValue("token") String tokenCookie) {
        return "You are logged in!";
    }

}
