package br.com.fcamara.apiorangejuice.services;

import br.com.fcamara.apiorangejuice.api.dtos.login.SuccessLoginResponse;
import br.com.fcamara.apiorangejuice.api.utils.converters.UserDtoConverter;
import br.com.fcamara.apiorangejuice.domain.entities.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static br.com.fcamara.apiorangejuice.api.utils.Constants.API_ISSUER;
import static br.com.fcamara.apiorangejuice.api.utils.Constants.ERROR_JWT_CREATION_MESSAGE;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final UserDtoConverter converter;

    @Value("${api.security.token.secret}")
    private String key;

    public SuccessLoginResponse createToken(User user) {
        try {
            var token = JWT.create()
                    .withIssuer(API_ISSUER)
                    .withSubject(user.getEmail())
                    .withExpiresAt(getExpirationTime())
                    .sign(getAlgorithm());
            return createLoginResponse(user, token);

        } catch (JWTCreationException exception) {
            throw new RuntimeException(ERROR_JWT_CREATION_MESSAGE);
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

    private Instant getExpirationTime() {
        return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(key);
    }

    private SuccessLoginResponse createLoginResponse(User user, String token) {
        var userResponse = converter.toUserResponse(user);
        return new SuccessLoginResponse(true, token, userResponse);
    }

    public UsernamePasswordAuthenticationToken getAuthenticationToken(String email, String password) {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
