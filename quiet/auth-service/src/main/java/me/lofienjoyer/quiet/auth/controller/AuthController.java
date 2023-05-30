package me.lofienjoyer.quiet.auth.controller;

import lombok.extern.slf4j.Slf4j;
import me.lofienjoyer.quiet.basemodel.dto.AuthRequest;
import me.lofienjoyer.quiet.auth.service.JwtService;
import me.lofienjoyer.quiet.auth.service.UserService;
import me.lofienjoyer.quiet.basemodel.dto.CreateUserDto;
import me.lofienjoyer.quiet.basemodel.dto.UserInfoDto;
import me.lofienjoyer.quiet.basemodel.entity.Role;
import me.lofienjoyer.quiet.basemodel.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

/**
 * Controller to handle authentication related requests
 */
@RestController
@Slf4j
public class AuthController {

    @Autowired
    private UserService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Test endpoint
     */
    @GetMapping("/welcome")
    @PreAuthorize("hasRole('ADMIN')")
    public String welcome() {
        return "If you see this you're admin, yay!";
    }

    /**
     * Endpoint for registering a user
     * @param dto DTO with the needed data to register a new user
     * @return Created UserInfo object
     */
    @PostMapping("/new")
    public UserInfo addNewUser(@RequestBody CreateUserDto dto) {
        return service.addUser(dto);
    }

    /**
     * Endpoint to get a user's token
     * @param authRequest DTO with the needed data to log in
     * @return Generated JWT token
     */
    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getEmail());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    /**
     * Endpoint to retrieve user info
     * @param token JWT token
     * @return DTO with user info
     */
    @GetMapping("/getuser")
    public UserInfoDto getUserInfo(@RequestParam("token") String token) {
        String email = null;
        try {
            email = jwtService.extractUsername(token);
        } catch (Exception e) {
            log.warn("Could not parse token " + token);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        UserInfo userInfo = service.getByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        UserInfoDto dto = new UserInfoDto();
        dto.setEmail(userInfo.getEmail());
        dto.setAuthorities(userInfo.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toList())
        );

        return dto;
    }

}
