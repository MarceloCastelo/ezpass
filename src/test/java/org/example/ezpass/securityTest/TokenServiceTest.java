package org.example.ezpass.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.example.ezpass.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TokenServiceTest {

    private TokenService tokenService;

    @Value("${api.security.token.secret}")
    private String secret;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", secret);
    }

    @Test
    public void generateToken_shouldGenerateValidToken() {
        User user = new User();
        user.setLogin("testuser");

        String token = tokenService.generateToken(user);
        assertNotNull(token);

        String subject = JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .getSubject();

        assertEquals("testuser", subject);
    }

    @Test
    public void validateToken_shouldReturnSubjectForValidToken() {
        String token = JWT.create()
                .withIssuer("auth-api")
                .withSubject("testuser")
                .withExpiresAt(Instant.now().plusSeconds(7200))
                .sign(Algorithm.HMAC256(secret));

        String subject = tokenService.validateToken(token);
        assertEquals("testuser", subject);
    }

    @Test
    public void validateToken_shouldReturnEmptyStringForInvalidToken() {
        String invalidToken = "invalidToken";
        String subject = tokenService.validateToken(invalidToken);
        assertEquals("", subject);
    }

    @Test
    public void generateToken_shouldThrowExceptionForJWTCreationError() {
        ReflectionTestUtils.setField(tokenService, "secret", "");
        User user = new User();
        user.setLogin("testuser");

        assertThrows(RuntimeException.class, () -> tokenService.generateToken(user));
    }

    @Test
    public void validateToken_shouldHandleJWTVerificationException() {
        String token = JWT.create()
                .withIssuer("auth-api")
                .withSubject("testuser")
                .withExpiresAt(Instant.now().minusSeconds(3600))
                .sign(Algorithm.HMAC256(secret)); // expired token

        String subject = tokenService.validateToken(token);
        assertEquals("", subject);
    }
}
