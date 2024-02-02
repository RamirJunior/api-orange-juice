package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.api.dtos.login.LoginResponse;
import br.com.fcamara.apiorangejuice.domain.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String key;
    private final String API_ISSUER = "orange-api-squad14";

    public LoginResponse createToken(User user) {
        try {
            var token = JWT.create()
                    .withIssuer(API_ISSUER)
                    .withSubject(user.getEmail())
                    .withExpiresAt(getExpirationTime())
                    .sign(getAlgorithm());
            return new LoginResponse(token);

        } catch (JWTCreationException exception) {
            throw new RuntimeException("Something wrong during token generating.");
        }
    }

    public String validateToken(String token) {
        try {
            return JWT.require(getAlgorithm())
                    .withIssuer(API_ISSUER)
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            return "";
        }
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(key);
    }

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(String email, String password) {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
