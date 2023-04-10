package me.lofienjoyer.quiet.auth.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.lofienjoyer.quiet.auth.service.JwtService;
import me.lofienjoyer.quiet.basemodel.dto.AuthRequest;
import me.lofienjoyer.quiet.basemodel.dto.LoginResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (!authentication.isAuthenticated())
            return ResponseEntity.ok(new LoginResponseDto(false, "Invalid login parameters."));

        Cookie cookie = new Cookie("token", jwtService.generateToken(authRequest.getEmail()));
        cookie.setPath("/");
        response.addCookie(cookie);

        return ResponseEntity.ok(new LoginResponseDto(true, "Successful login."));
    }

    @GetMapping("/test")
    @PreAuthorize("isAuthenticated()")
    public String test() {
        return "Hola";
    }

}
