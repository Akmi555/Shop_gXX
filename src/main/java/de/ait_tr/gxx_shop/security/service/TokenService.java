package de.ait_tr.gxx_shop.security.service;
/*
@date 18.07.2024
@author Sergey Bugaienko
*/

import de.ait_tr.gxx_shop.domain.entity.Role;
import de.ait_tr.gxx_shop.domain.entity.User;
import de.ait_tr.gxx_shop.repository.RoleRepository;
import de.ait_tr.gxx_shop.security.AuthInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TokenService {
    private RoleRepository roleRepository;
    private SecretKey accessKey;
    private SecretKey refreshKey;

    public TokenService(RoleRepository roleRepository,
                        @Value("${key.access}") String accessSecretPhrase,
                        @Value("${key.refresh}") String refreshSecretPhrase) {
        this.roleRepository = roleRepository;
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessSecretPhrase));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshSecretPhrase));
        ;
    }

    public String generateAccessToken(User user) {

        Instant now = Instant.now();
        Instant expiration = now.plus(7, ChronoUnit.DAYS);

        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getUserName())
                .expiration(expirationDate)
                .signWith(accessKey)
                .claim("roles", user.getRoles())
                .claim("name", user.getUserName())
                .compact();
    }

    public String generateRefreshToken(User user) {

        Instant now = Instant.now();
        Instant expiration = now.plus(30, ChronoUnit.DAYS);
        Date expirationDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getUserName())
                .expiration(expirationDate)
                .signWith(refreshKey)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshKey);
    }

    private boolean validateToken(String token, SecretKey secretKey) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token, SecretKey key) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Claims getAccessClaims(String accessToken) {
        return getClaims(accessToken, accessKey);
    }

    public Claims getRefreshClaims(String refreshToken) {
        return getClaims(refreshToken, refreshKey);
    }

    public AuthInfo mapClaimsToAuthinfo(Claims claims) {
        String username = claims.getSubject();

        @SuppressWarnings("unchecked")
        List<Map<String, String>> rolesList = (List<Map<String, String>>) claims.get("roles", List.class);

        Set<Role> authorities = rolesList.stream()
                .map(m -> m.get("authority"))
                .map(s -> roleRepository.findByTitle(s).orElseThrow(() -> new RuntimeException("Database doesn't contain role")))
                .collect(Collectors.toSet());

        return new AuthInfo(username, authorities);
    }

}
