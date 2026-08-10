package com.fernando.estoque_api.service;

import org.springframework.stereotype.Service;

import com.fernando.estoque_api.dto.usuario.UsuarioRequestDTO;
import com.fernando.estoque_api.dto.usuario.UsuarioResponseDTO;
import com.fernando.estoque_api.entity.Usuario;
import com.fernando.estoque_api.exception.ResourceAlreadyExistsException;
import com.fernando.estoque_api.exception.ResourceNotFoundException;
import com.fernando.estoque_api.mapper.UsuarioMapper;
import com.fernando.estoque_api.repository.UsuarioRepository;
import com.fernando.estoque_api.security.PasswordService;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class UsuarioService {
    
    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;
    private final PasswordService passwordService;
    
    public UsuarioService(UsuarioRepository usuarioRepository,UsuarioMapper usuarioMapper,PasswordService passwordService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
        this.passwordService = passwordService;
    }
    public UsuarioResponseDTO criarUsuario(UsuarioRequestDTO dto){
        if(usuarioRepository.existsByEmail(dto.getEmail())){
            throw new ResourceAlreadyExistsException("Email ja cadastrado.");
        }
        Usuario usuario = new Usuario();
        usuario.setName(dto.getName());
        usuario.setEmail(dto.getEmail());
        usuario.setRole(dto.getRole());
        usuario.setPassword(passwordService.encode(dto.getPassword()));
        Usuario usuarioSalvo = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioSalvo);
    }
    public UsuarioResponseDTO buscarUsuarioPorId(Long id){
       
        Usuario usuario = usuarioRepository.findByIdAndDeletedAtIsNull(id)
        .orElseThrow(()-> new ResourceNotFoundException("Usuario nao encontrado"));
       
        return usuarioMapper.toDTO(usuario);
    }
    public UsuarioResponseDTO buscarUsuarioPorEmail(String email){
       
        Usuario usuario = usuarioRepository.findByEmailAndDeletedAtIsNull(email)
        .orElseThrow(()-> new ResourceNotFoundException("Email nao encontrado"));
       
        return usuarioMapper.toDTO(usuario);
    }
    public List<UsuarioResponseDTO> listarUsuarios(){
        List<Usuario> usuarios = usuarioRepository.findByDeletedAtIsNull();
        List<UsuarioResponseDTO> response = usuarios.stream().map(usuarioMapper::toDTO).toList();
        return response;
    }
    public UsuarioResponseDTO atualizarUsuarioPorId(Long id, UsuarioRequestDTO dto){
        
        Usuario usuario = usuarioRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()-> new ResourceNotFoundException("Usuario nao encontrado"));
        if(usuarioRepository.existsByEmail(dto.getEmail()) && !usuario.getEmail().equals(dto.getEmail())){
            throw new ResourceAlreadyExistsException("Email ja cadastrado.");
        }
        usuario.setName(dto.getName());
        usuario.setEmail(dto.getEmail());
        usuario.setRole(dto.getRole());
        usuario.setPassword(passwordService.encode(dto.getPassword()));
        Usuario usuarioAtualizado = usuarioRepository.save(usuario);

        return usuarioMapper.toDTO(usuarioAtualizado);
    }
    public void deletarUsuario(Long id){

        Usuario usuario = usuarioRepository.findByIdAndDeletedAtIsNull(id).orElseThrow(()->new ResourceNotFoundException("Usuario nao encontrado."));
        
        usuario.setDeletedAt(LocalDateTime.now());
        usuarioRepository.save(usuario);

    }
}

