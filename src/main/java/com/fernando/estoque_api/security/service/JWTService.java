package com.fernando.estoque_api.security.service;
import io.jsonwebtoken.Jwts;

import java.sql.Date;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

@Service
public class JwtService {
    private SecretKey key = Jwts.SIG.HS256.key().build();

    public String generateToken(String data){
        Date iat = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() +3600000);
        return Jwts.builder()
                    .subject(data)
                    .issuedAt(iat)
                    .expiration(expiration)
                    .signWith(key)
                    .compact();
    }
    public boolean validateToken(String jwt, String data){
        return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(jwt)
                    .getPayload()
                    .getSubject()
                    .equals(data);  
    }
    public String extractSubject(String jwt){
         return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(jwt)
            .getPayload()
            .getSubject();
    }
}
