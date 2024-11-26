package com.Airtribe.EmployeeTrackingSystem.AuthenticationAuthorization.jwtUtil;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;


public class JwtUtil {
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long expiration = 86400000L;

    public static String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .signWith(key)
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .setIssuedAt(new Date())
                .compact();
    }
}
