package com.sagun.expense_tracker_api.utils;

import com.sagun.expense_tracker_api.principal.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtils {



    private final long accessTokenExpiration = 900000;
    private final long refreshTokenExpiration = 604800000;
    @Value("${JJWT_SECRET_KEY}")
    private  String SECRET;
    private SecretKey key;


    public String generateRefreshToken(String email){
        return generateToken(email, refreshTokenExpiration,"refresh");
    }

    public String generateAccessToken(String email){
        return generateToken(email,accessTokenExpiration, "access");
    }

    private final String generateToken(String email, long expiration,String tokenType){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);
        return Jwts.builder()
                .subject(email)
                .claim("type",tokenType)
                .signWith(key)
                .setIssuedAt(now)
                .expiration(expiryDate)
                .compact();
    }

    public Claims extractClaims(String token){
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getEmail(String token){
        return extractClaims(token).getSubject();
    }

    public boolean isTokenExpired(String token){
        return extractClaims(token).getExpiration().before(new Date());
    }

    public boolean isValidToken(String email, String token, UserPrincipal userDetails){
        Claims claims = extractClaims(token);
        String tokenType = claims.get("type", String.class);
        return email.equals(userDetails.getEmail()) && "access".equals(tokenType) && !isTokenExpired(token);
    }

    @PostConstruct
    public void init(){
        this.key  = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

}

