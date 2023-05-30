package me.lofienjoyer.quiet.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.auth.service.JwtService;
import me.lofienjoyer.quiet.basemodel.dto.AuthRequest;
import me.lofienjoyer.quiet.basemodel.dto.LoginResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

/**
 * Handles login requests
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    /**
     * @param authRequest DTO with the needed data to log in
     * @param response HTTP response
     * @return DTO with the needed data to register a new user
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid email or password.");
        }

        if (!authentication.isAuthenticated())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid email or password.");

        Cookie cookie = new Cookie("token", jwtService.generateToken(authRequest.getEmail()));
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponseDto(true, "OK"));
    }

}
