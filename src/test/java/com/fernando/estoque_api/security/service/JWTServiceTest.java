package com.fernando.estoque_api.security.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class JWTServiceTest {
    private JWTService jwtService = new JWTService();
    @Test void shouldGenerateTokenSuccessfully() {
        String email = "fernando@email.com";

        String token = jwtService.generateToken(email);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }
    @Test void shouldValidateValidToken() {
        String email = "fernando@email.com";

        String token = jwtService.generateToken(email);

        assertTrue(jwtService.validateToken(token, email));
    }
    @Test
        void shouldRejectTokenWhenSubjectDoesNotMatch() {
        String token = jwtService.generateToken("fernando@email.com");

        assertFalse(jwtService.validateToken(token, "outro@email.com"));
    }
}
