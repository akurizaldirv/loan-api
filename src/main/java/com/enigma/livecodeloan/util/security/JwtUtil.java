package com.enigma.livecodeloan.util.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.livecodeloan.model.entity.AppUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${api.enigmat-shop.jwt.jwt-secret}")
    private String SECRET;

    @Value("${api.enigmat-shop.jwt.app-name}")
    private String APP_NAME;

    @Value("${api.enigmat-shop.jwt.jwt-expiration}")
    private Long EXPIRATION;

    public String generateToken(AppUser appUser) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
            return JWT.create() // Membuat JWT Token
                    .withIssuer(APP_NAME)
                    .withSubject(appUser.getId())
                    .withExpiresAt(Instant.now().plusSeconds(EXPIRATION))
                    .withIssuedAt(Instant.now())
                    .withClaim("role", appUser.getRoles())
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException();
        }
    }

    public Boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));

            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            return decodedJWT.getIssuer().equals(APP_NAME);
        } catch (JWTVerificationException e) {
            throw new RuntimeException();
        }
    }

    public Map<String, String> getUserInfoByToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));

            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            Map<String, String> info = new HashMap<>();
            info.put("userId", decodedJWT.getSubject());
            info.put("role", decodedJWT.getClaim("role").asString());
            return info;
        } catch (JWTVerificationException e) {
            throw new RuntimeException();
        }
    }
}
