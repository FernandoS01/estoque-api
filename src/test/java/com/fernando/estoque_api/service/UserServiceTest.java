package com.fernando.estoque_api.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.mockito.ArgumentMatchers.any;

import com.fernando.estoque_api.dto.user.UserRequestDTO;
import com.fernando.estoque_api.dto.user.UserResponseDTO;
import com.fernando.estoque_api.entity.User;
import com.fernando.estoque_api.enums.Role;
import com.fernando.estoque_api.exception.ResourceAlreadyExistsException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.UserMapper;
import com.fernando.estoque_api.repository.UserRepository;
import com.fernando.estoque_api.security.service.PasswordService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock 
    private UserMapper userMapper;
    @Mock
    private PasswordService passwordService;
  
    @InjectMocks
    private UserService userService;

    @Test void shouldCreateUserSucessfully(){
                
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Juliana");
        request.setEmail("pequena@email.com");
        request.setPassword("teste");
        request.setRole(Role.ADMIN);
    

        User usuario = new User();
        usuario.setName("Juliana");
        usuario.setEmail("pequena@email.com");
        usuario.setPassword("hash-gerado");
        usuario.setRole(Role.ADMIN);
        
        UserResponseDTO response = new UserResponseDTO();
        response.setName("Juliana");
        response.setEmail("pequena@email.com");
        response.setRole(Role.ADMIN);
        
        when(userRepository.existsByEmail("pequena@email.com")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(usuario);
        when(userMapper.toDTO(usuario)).thenReturn(response);
        when(passwordService.encode("teste")).thenReturn("hash-gerado");

        UserResponseDTO results = userService.createUser(request);

        
        assertEquals("Juliana", results.getName());
        assertEquals("pequena@email.com", results.getEmail());
        assertEquals(Role.ADMIN, results.getRole());
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(captor.capture());
        assertEquals("hash-gerado", captor.getValue().getPassword());
        verify(userRepository).save(any(User.class));
    }
    @Test void shouldThrowExceptionWhenEmailAlreadyExists(){
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Juliana");
        request.setEmail("pequena@email.com");

        when(userRepository.existsByEmail("pequena@email.com")).thenReturn(true);
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, ()->{
            userService.createUser(request);
        });
        assertEquals("Email já cadastrado.", exception.getMessage());

        verify(userRepository,never()).save(any(User.class));
        verify(userMapper,never()).toDTO(any(User.class));
    }
    @Test void shouldThowExceptionWhenIdNotExists(){
        Long id = 114L;
        when(userRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            userService.findUserById(id);
        });
        assertEquals("Usuário não encontrado.", exception.getMessage());
        verify(userRepository,never()).save(any(User.class));
        verify(userMapper,never()).toDTO(any(User.class));
    }
    @Test void shouldThowExceptionWhenEmailNotExists(){
        String email = "fernando@email.com";
        when(userRepository.findByEmailAndDeletedAtIsNull(email)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            userService.findUserByEmail(email);
        });
        assertEquals("Email não encontrado.", exception.getMessage());
        verify(userRepository,never()).save(any(User.class));
        verify(userMapper,never()).toDTO(any(User.class));
    }
    @Test void shouldReturnUserByEmail(){
        User usuario = new User();
        usuario.setName("Juliana");
        usuario.setEmail("pequena@email.com");
        usuario.setRole(Role.ADMIN);

        UserResponseDTO response = new UserResponseDTO();
        response.setName("Juliana");
        response.setEmail("pequena@email.com");
        response.setRole(Role.ADMIN);

        when(userRepository.findByEmailAndDeletedAtIsNull("pequena@email.com")).thenReturn(Optional.of(usuario));
        when(userMapper.toDTO(usuario)).thenReturn(response);

        UserResponseDTO results = userService.findUserByEmail("pequena@email.com");

        assertEquals("Juliana", results.getName());
        assertEquals("pequena@email.com", results.getEmail());
        assertEquals(Role.ADMIN, results.getRole());

        verify(userRepository).findByEmailAndDeletedAtIsNull("pequena@email.com");
        verify(userMapper).toDTO(usuario);
    }
    @Test void shouldReturnUserById(){
        Long id = 150L;

        User usuario = new User();
        usuario.setName("Juliana");
        usuario.setEmail("pequena@email.com");
        usuario.setRole(Role.ADMIN);

        UserResponseDTO response = new UserResponseDTO();
        response.setName("Juliana");
        response.setEmail("pequena@email.com");
        response.setRole(Role.ADMIN);

        when(userRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(usuario));
        when(userMapper.toDTO(usuario)).thenReturn(response);

        UserResponseDTO results = userService.findUserById(id);

        assertEquals("Juliana", results.getName());
        assertEquals("pequena@email.com", results.getEmail());
        assertEquals(Role.ADMIN, results.getRole());

        verify(userRepository).findByIdAndDeletedAtIsNull(id);
        verify(userMapper).toDTO(usuario);
    }
    @Test void shouldReturnUsers(){

        User usuario1 = new User();
        User usuario2 = new User();

        UserResponseDTO response1 = new UserResponseDTO();
        UserResponseDTO response2 = new UserResponseDTO();

        when(userRepository.findByDeletedAtIsNull()).thenReturn(List.of(usuario1,usuario2));
        when(userMapper.toDTO(usuario1)).thenReturn(response1);
        when(userMapper.toDTO(usuario2)).thenReturn(response2);

        List<UserResponseDTO> results = userService.findAllUsers();

        assertEquals(2, results.size());
    }
    @Test void shouldUpadateUserById(){

        Long id = 150L;
        UserRequestDTO request = new UserRequestDTO();
        request.setName("Fernando");
        request.setEmail("fernando@email.com");
        request.setRole(Role.ADMIN);
        request.setPassword("senha");

        User usuario = new User();
        usuario.setId(id);
        usuario.setName("Juliana");
        usuario.setEmail("pequena@email.com");
        usuario.setPassword("senha-anterior");
        usuario.setRole(Role.EMPLOYEE);

        UserResponseDTO response = new UserResponseDTO();
        response.setName("Fernando");
        response.setEmail("fernando@email.com");
        response.setRole(Role.ADMIN);

        when(userRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(usuario));
        when(userRepository.save(any(User.class))).thenReturn(usuario);
        when(userMapper.toDTO(usuario)).thenReturn(response);

        UserResponseDTO results = userService.updateUserById(id, request);

        assertEquals("Fernando", results.getName());
        assertEquals("fernando@email.com", results.getEmail());
        assertEquals(Role.ADMIN, results.getRole());

        verify(userRepository).save(any(User.class));
        verify(userMapper).toDTO(usuario);

    }
    @Test void shouldDeleteUser(){
        Long id = 150L;
        User usuario = new User();
        usuario.setId(id);
        when(userRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(usuario));
        userService.deleteUser(id);
        
        verify(userRepository).save(any(User.class));
        verify(userRepository).findByIdAndDeletedAtIsNull(id);

    }
}

// Arrange
// Act
// Assert
// verificar o resultado
// verificar as interações