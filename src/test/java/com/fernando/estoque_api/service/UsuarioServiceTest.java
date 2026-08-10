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

import com.fernando.estoque_api.dto.usuario.UsuarioRequestDTO;
import com.fernando.estoque_api.dto.usuario.UsuarioResponseDTO;
import com.fernando.estoque_api.entity.Usuario;
import com.fernando.estoque_api.enums.Role;
import com.fernando.estoque_api.exception.ResourceAlreadyExistsException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.UsuarioMapper;
import com.fernando.estoque_api.repository.UsuarioRepository;
import com.fernando.estoque_api.security.PasswordService;

@ExtendWith(MockitoExtension.class)
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock 
    private UsuarioMapper usuarioMapper;
    @Mock
    private PasswordService passwordService;
  
    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void deveCriarUsuarioComSucesso(){
                
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setName("Juliana");
        request.setEmail("pequena@email.com");
        request.setPassword("teste");
        request.setRole(Role.ADMIN);
    

        Usuario usuario = new Usuario();
        usuario.setName("Juliana");
        usuario.setEmail("pequena@email.com");
        usuario.setPassword("hash-gerado");
        usuario.setRole(Role.ADMIN);
        
        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setName("Juliana");
        response.setEmail("pequena@email.com");
        response.setRole(Role.ADMIN);
        
        when(usuarioRepository.existsByEmail("pequena@email.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDTO(usuario)).thenReturn(response);
        when(passwordService.encode("teste")).thenReturn("hash-gerado");

        UsuarioResponseDTO results = usuarioService.criarUsuario(request);

        
        assertEquals("Juliana", results.getName());
        assertEquals("pequena@email.com", results.getEmail());
        assertEquals(Role.ADMIN, results.getRole());
        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(usuarioRepository).save(captor.capture());
        assertEquals("hash-gerado", captor.getValue().getPassword());
        verify(usuarioRepository).save(any(Usuario.class));
    }
    @Test
    void deveRetornarExcecaoSeEmailJaExistir(){
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setName("Juliana");
        request.setEmail("pequena@email.com");

        when(usuarioRepository.existsByEmail("pequena@email.com")).thenReturn(true);
        ResourceAlreadyExistsException exception = assertThrows(ResourceAlreadyExistsException.class, ()->{
            usuarioService.criarUsuario(request);
        });
        assertEquals("Email ja cadastrado.", exception.getMessage());

        verify(usuarioRepository,never()).save(any(Usuario.class));
        verify(usuarioMapper,never()).toDTO(any(Usuario.class));
    }
    @Test
    void deveRetornarExcecaoSeIdNaoExistir(){
        Long id = 114L;
        when(usuarioRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            usuarioService.buscarUsuarioPorId(id);
        });
        assertEquals("Usuario nao encontrado", exception.getMessage());
        verify(usuarioRepository,never()).save(any(Usuario.class));
        verify(usuarioMapper,never()).toDTO(any(Usuario.class));
    }
     @Test
    void deveRetornarExcecaoSeEmailNaoExistir(){
        String email = "fernando@email.com";
        when(usuarioRepository.findByEmailAndDeletedAtIsNull(email)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, ()->{
            usuarioService.buscarUsuarioPorEmail(email);
        });
        assertEquals("Email nao encontrado", exception.getMessage());
        verify(usuarioRepository,never()).save(any(Usuario.class));
        verify(usuarioMapper,never()).toDTO(any(Usuario.class));
    }
    @Test
    void deveRetornarUsuarioPorEmail(){

        Usuario usuario = new Usuario();
        usuario.setName("Juliana");
        usuario.setEmail("pequena@email.com");
        usuario.setRole(Role.ADMIN);

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setName("Juliana");
        response.setEmail("pequena@email.com");
        response.setRole(Role.ADMIN);

        when(usuarioRepository.findByEmailAndDeletedAtIsNull("pequena@email.com")).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(response);

        UsuarioResponseDTO results = usuarioService.buscarUsuarioPorEmail("pequena@email.com");

        assertEquals("Juliana", results.getName());
        assertEquals("pequena@email.com", results.getEmail());
        assertEquals(Role.ADMIN, results.getRole());

        verify(usuarioRepository).findByEmailAndDeletedAtIsNull("pequena@email.com");
        verify(usuarioMapper).toDTO(usuario);
    }
    @Test
    void deveRetornarUsuarioPorId(){
        Long id = 150L;

        Usuario usuario = new Usuario();
        usuario.setName("Juliana");
        usuario.setEmail("pequena@email.com");
        usuario.setRole(Role.ADMIN);

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setName("Juliana");
        response.setEmail("pequena@email.com");
        response.setRole(Role.ADMIN);

        when(usuarioRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDTO(usuario)).thenReturn(response);

        UsuarioResponseDTO results = usuarioService.buscarUsuarioPorId(id);

        assertEquals("Juliana", results.getName());
        assertEquals("pequena@email.com", results.getEmail());
        assertEquals(Role.ADMIN, results.getRole());

        verify(usuarioRepository).findByIdAndDeletedAtIsNull(id);
        verify(usuarioMapper).toDTO(usuario);
    }
    @Test
    void deveRetornarUsuariosComSucesso(){

        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();

        UsuarioResponseDTO response1 = new UsuarioResponseDTO();
        UsuarioResponseDTO response2 = new UsuarioResponseDTO();

        when(usuarioRepository.findByDeletedAtIsNull()).thenReturn(List.of(usuario1,usuario2));
        when(usuarioMapper.toDTO(usuario1)).thenReturn(response1);
        when(usuarioMapper.toDTO(usuario2)).thenReturn(response2);

        List<UsuarioResponseDTO> results = usuarioService.listarUsuarios();

        assertEquals(2, results.size());
    }
    @Test
    void deveAtualizarUsuarioPorIdComSucesso(){

        Long id = 150L;
        UsuarioRequestDTO request = new UsuarioRequestDTO();
        request.setName("Fernando");
        request.setEmail("fernando@email.com");
        request.setRole(Role.ADMIN);
        request.setPassword("senha");

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setName("Juliana");
        usuario.setEmail("pequena@email.com");
        usuario.setPassword("senha-anterior");
        usuario.setRole(Role.FUNCIONARIO);

        UsuarioResponseDTO response = new UsuarioResponseDTO();
        response.setName("Fernando");
        response.setEmail("fernando@email.com");
        response.setRole(Role.ADMIN);

        when(usuarioRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(usuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        when(usuarioMapper.toDTO(usuario)).thenReturn(response);

        UsuarioResponseDTO results = usuarioService.atualizarUsuarioPorId(id, request);

        assertEquals("Fernando", results.getName());
        assertEquals("fernando@email.com", results.getEmail());
        assertEquals(Role.ADMIN, results.getRole());

        verify(usuarioRepository).save(any(Usuario.class));
        verify(usuarioMapper).toDTO(usuario);

    }
    @Test
    void deveDeletarUsuario(){
        Long id = 150L;
        Usuario usuario = new Usuario();
        usuario.setId(id);
        when(usuarioRepository.findByIdAndDeletedAtIsNull(id)).thenReturn(Optional.of(usuario));
        usuarioService.deletarUsuario(id);
        
        verify(usuarioRepository).save(any(Usuario.class));
        verify(usuarioRepository).findByIdAndDeletedAtIsNull(id);

    }
}

// Arrange
// Act
// Assert
// verificar o resultado
// verificar as interações