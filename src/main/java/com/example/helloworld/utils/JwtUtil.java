package com.example.helloworld.utils;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    private final String SECERT="ThisIsMySuperStrongJwtSecretKey123456";
    private final long EXPIRATION=1000*60*60;
    private final Key SecertKey= Keys.hmacShaKeyFor(SECERT.getBytes(StandardCharsets.UTF_8));

    public String generateToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRATION))
                .signWith(SecertKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        return  Jwts.parserBuilder()
                .setSigningKey(SecertKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

    }
    public boolean validateJwtToken(String token){
        try{
            Jwts.parserBuilder()
                    .setSigningKey(SecertKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return  true;
        }
        catch (JwtException exception){
            return false;
        }
    }

}
