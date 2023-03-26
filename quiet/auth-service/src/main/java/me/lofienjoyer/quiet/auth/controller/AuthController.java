package me.lofienjoyer.quiet.auth.controller;

import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.auth.dto.CreateUserDto;
import me.lofienjoyer.quiet.auth.dto.GetTokenDto;
import me.lofienjoyer.quiet.auth.model.User;
import me.lofienjoyer.quiet.auth.service.AuthService;
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
    public String getToken(@RequestBody GetTokenDto dto) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword()));
        if (authentication.isAuthenticated()) {
            return authService.generateToken(dto.getEmail());
        } else {
            throw new UsernameNotFoundException("User " + dto.getEmail() + " not found");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        authService.validateToken(token);
        return "The token is valid.";
    }

}
