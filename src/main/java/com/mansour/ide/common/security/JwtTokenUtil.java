package com.mansour.ide.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    private final String secretString = "oyET2R4Mh7TV8FlYcSzcnucv5nh4TJnarWf0btpLZpN/CqGnyM/zhvONr2cpUrBeROlB/LtW8a9SYz2QfS22tw==";
    private final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretString));
    private long accessTokenValidity = 24 * 60 * 60 * 1000;
    private long refreshTokenValidity = 7 * 24 * 60 * 60 * 1000;

    public boolean validateRefreshToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);

            // Extract the claims body from the parsed token
            Claims claims = claimsJws.getBody();

            // Check if the token is expired
            return !claims.getExpiration().before(new Date());
        } catch (JwtException ex) { // This catches all JWT-related exceptions, which could include expired,
                                    // unsupported, or illegal argument exceptions
            // Log the exception to investigate further - consider using a logger here
            return false;
        }
    }

    public String generateAccessToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername(), accessTokenValidity);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return generateToken(userDetails.getUsername(), refreshTokenValidity);
    }

    // 토큰 생성 로직
    private String generateToken(String subject, long validity) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validity))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰에서 사용자 이름 추출
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    // 토큰 유효성 검증
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    // 토큰에서 정보 추출
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token).getBody();
    }

    // 토큰의 만료일 확인
    public Boolean isTokenExpired(String token) {
        final Date expiration = getAllClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

}
