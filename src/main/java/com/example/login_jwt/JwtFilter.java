package com.example.login_jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        final String authHeader = request.getHeader("Authorization");

        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (!jwtUtil.validateToken(token)){
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Invalido");
                    return;
            }
        } else if (!request.getRequestURI().equals("/login")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token Requerido");
                return;
        }
        filterChain.doFilter(request, response);
    }
}
