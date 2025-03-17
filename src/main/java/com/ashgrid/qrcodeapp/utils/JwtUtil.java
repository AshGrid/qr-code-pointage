package com.ashgrid.qrcodeapp.utils;

import com.ashgrid.qrcodeapp.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Secret key, should be stored securely
    private final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey";

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Generate the JWT token with roles
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole().name()) // Include role as a claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours expiration
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extract email from the JWT token
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extract roles from the JWT token
    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    // Extract all claims from the token
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Validate the token
    public boolean validateToken(String token, User user) {
        final String email = extractEmail(token);
        final String role = extractRole(token);
        return email.equals(user.getEmail()) && !isTokenExpired(token);
    }

    // Check if the token is expired
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
