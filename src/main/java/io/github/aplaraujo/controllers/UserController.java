package io.github.aplaraujo.controllers;

import io.github.aplaraujo.entities.AuthRequest;
import io.github.aplaraujo.entities.User;
import io.github.aplaraujo.security.UserDetailsServiceImpl;
import io.github.aplaraujo.services.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserDetailsServiceImpl service;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @GetMapping("/admin")
    public String adminRoute() {
        return "Admin route!";
    }

    @GetMapping("/debug")
    public ResponseEntity<?> debug() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok(Map.of(
                "username", auth.getName(),
                "authorities", auth.getAuthorities()
        ));
    }

    @PostMapping("/new-user")
    public String addNewUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        System.out.println("=== CHEGOU NO CONTROLLER ===");
        System.out.println("Email recebido: " + authRequest.getEmail());
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            System.out.println("=== AUTENTICAÇÃO OK ===");
            if (authentication.isAuthenticated()) {
                return jwtService.generateToken(authRequest.getEmail());
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (Exception e) {
            System.out.println("=== ERRO NA AUTENTICAÇÃO: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

    }
}
