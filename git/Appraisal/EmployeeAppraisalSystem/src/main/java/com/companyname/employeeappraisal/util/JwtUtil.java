package com.companyname.employeeappraisal.util;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
    private static final String SECRET = "5pAq6zRyX8bC3dV2wS7gN1mK9jF0hL4tUoP6iBvE3nG8xZaQrY7cW2fA";
    private static final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());
    public String generateToken(Integer employeeId,String shortName) {
        return Jwts.builder()
                .claim("employeeId", employeeId)
                .claim("role", shortName)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) 
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


  
    public String extractEmployeeId(String token) {
        Integer empId = extractAllClaims(token).get("employeeId", Integer.class);
        return empId != null ? empId.toString() : null;
    }


    public String extractRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }

    public boolean isTokenValid(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
