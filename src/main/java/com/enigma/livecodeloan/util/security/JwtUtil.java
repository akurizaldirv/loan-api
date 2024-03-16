package com.enigma.livecodeloan.util.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.enigma.livecodeloan.model.entity.AppUser;
import com.enigma.livecodeloan.model.entity.User;
import com.enigma.livecodeloan.model.entity.UserRole;
import com.enigma.livecodeloan.util.enums.ERole;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
//@RequiredArgsConstructor
public class JwtUtil {

//    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Value("${api.enigmat-shop.jwt.jwt-secret}")
    private String SECRET;

    @Value("${api.enigmat-shop.jwt.app-name}")
    private String APP_NAME;

    @Value("${api.enigmat-shop.jwt.jwt-expiration}")
    private Long EXPIRATION;

    public String generateToken(AppUser user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));
            List<String> roles = new ArrayList<>();
            for (ERole role : user.getRoles()) {
                roles.add(role.name());
            }
            return JWT.create() // Membuat JWT Token
                    .withIssuer(APP_NAME)
                    .withSubject(user.getId())
                    .withExpiresAt(Instant.now().plusSeconds(EXPIRATION))
                    .withIssuedAt(Instant.now())
                    .withClaim("role", roles)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new ValidationException("Failed generating token");
        }
    }

    public Boolean verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET.getBytes(StandardCharsets.UTF_8));

            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(token);

            return decodedJWT.getIssuer().equals(APP_NAME);
        } catch (JWTVerificationException e) {
            return false;
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
            throw new ValidationException("Failed fetching data from token");
        }
    }
}
