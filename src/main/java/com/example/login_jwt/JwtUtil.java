package com.example.login_jwt;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
    private final String SECRET_KEY="mi_clave_secreta";
    
    public String generateToken(String username) {
        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis() + 1000 * 60 * 30)) //tiempo de duración 30 minutos
        .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
        .compact();
    }

    public String extractUsername(String token){
        return Jwts.parser().setSigningKey(SECRET_KEY)
        .parseClaimsJws(token).getBody().getSubject();
    }

    public Boolean validateToken(String Token) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(Token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }  
}
