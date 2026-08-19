package com.fernando.estoque_api.security.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fernando.estoque_api.entity.User;
import com.fernando.estoque_api.enums.Role;
import com.fernando.estoque_api.exception.BusinessException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.repository.UserRepository;
import com.fernando.estoque_api.security.dto.authentication.AuthenticationRequestDTO;
import com.fernando.estoque_api.security.dto.authentication.AuthenticationResponseDTO;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {
    @Mock private UserRepository userRepository;
    @Mock private PasswordService passwordService;
    @Mock private JwtService jwtService;
    @InjectMocks private AuthenticationService authenticationService;

    @Test void shouldLoginSucessfully(){
        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        request.setEmail("user@email.com");
        request.setPassword("userPassword");

        User user = new User();
        user.setName("user1");
        user.setEmail("user@email.com");
        user.setPassword("userPassword");
        user.setRole(Role.ADMIN);
        
        when(userRepository.findByEmailAndDeletedAtIsNull("user@email.com")).thenReturn(Optional.of(user));
        when(passwordService.verifyPassword(request.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtService.generateToken("user@email.com")).thenReturn("tokenficticioapenasparatestes");
        AuthenticationResponseDTO results = authenticationService.authenticate(request);

        assertEquals("user1", results.getName());
        assertEquals(Role.ADMIN, results.getRole());
        assertNotNull(results.getToken());
        assertFalse(results.getToken().isBlank());
        verify(userRepository).findByEmailAndDeletedAtIsNull("user@email.com");
        verify(passwordService).verifyPassword("userPassword", "userPassword");
        verify(jwtService).generateToken("user@email.com");
    }
    @Test void shouldThrowExceptionWhenUserNotExists(){
        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        request.setEmail("user@email.com");
        
        when(userRepository.findByEmailAndDeletedAtIsNull("user@email.com")).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            authenticationService.authenticate(request);
        });
        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(jwtService,never()).generateToken(any(String.class));
        verify(passwordService,never()).verifyPassword(any(String.class), any(String.class));
    }
    @Test void shouldThrowExceptionWhenPasswordIsInvalid(){
        AuthenticationRequestDTO request = new AuthenticationRequestDTO();
        request.setEmail("user@email.com");
        request.setPassword("passwordWrong");

        User user = new User();
        user.setPassword("passwordUser");
        
        when(userRepository.findByEmailAndDeletedAtIsNull("user@email.com")).thenReturn(Optional.of(user));
        BusinessException exception = assertThrows(BusinessException.class, ()->{
            authenticationService.authenticate(request);
        });
        assertEquals("Senha invalida.", exception.getMessage());
        verify(jwtService,never()).generateToken(any(String.class));
    }
}
