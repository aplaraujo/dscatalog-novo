package io.github.aplaraujo.security;

import io.github.aplaraujo.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final UserDetailsServiceImpl service;
    private final JwtService jwtService;

    public JwtAuthFilter(@Lazy UserDetailsServiceImpl service, JwtService jwtService) {
        this.service = service;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        System.out.println("=== PATH: " + path);
        if (path.equals("/auth/token") ||
                path.equals("/auth/new-user") ||
                path.equals("/auth/welcome") ||
                path.startsWith("/h2-console")) {
            System.out.println("=== PULANDO FILTRO PARA: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("=== PROCESSANDO AUTENTICAÇÃO PARA: " + path);

        try {
            String authHeader = request.getHeader("Authorization");
            System.out.println("=== AUTHORIZATION HEADER: " + authHeader);
            String token = null;
            String email = null;

            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                token = authHeader.substring(7);
                System.out.println("=== TOKEN EXTRAÍDO: " + token.substring(0, Math.min(20, token.length())) + "...");
                email = jwtService.extractEmail(token);
                System.out.println("=== EMAIL EXTRAÍDO DO TOKEN: " + email);
            } else {
                System.out.println("=== SEM TOKEN OU FORMATO INVÁLIDO");
            }

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                System.out.println("=== CARREGANDO USER DETAILS PARA: " + email);
                UserDetails userDetails = service.loadUserByUsername(email);
                boolean isValid = jwtService.validateToken(token, userDetails);
                System.out.println("=== TOKEN VÁLIDO? " + isValid);
                if (jwtService.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    System.out.println("=== AUTHORITIES SETADAS: " + userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    System.out.println("=== AUTENTICAÇÃO CONFIGURADA COM SUCESSO");
                }
            }
        } catch (Exception e) {
            System.out.println("=== ERRO NO FILTRO: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("JwtAuthFilter error: " + e.getMessage());
        }

        System.out.println("=== PROSSEGUINDO COM FILTER CHAIN");
        filterChain.doFilter(request, response);
    }
}
