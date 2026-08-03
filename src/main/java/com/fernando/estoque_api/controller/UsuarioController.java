package com.fernando.estoque_api.controller;

import com.fernando.estoque_api.service.UsuarioService;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.estoque_api.dto.usuario.UsuarioRequestDTO;
import com.fernando.estoque_api.dto.usuario.UsuarioResponseDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/")
    public UsuarioResponseDTO criarUsuario(@RequestBody UsuarioRequestDTO usuario) {
        return usuarioService.criarUsuario(usuario);
    }
    @GetMapping("/{id}")
    public UsuarioResponseDTO buscarUsuarioPorId(@PathVariable Long id) {
        return usuarioService.buscarUsuarioPorId(id);
    }
    @GetMapping("/email/{email}")
    public UsuarioResponseDTO buscarUsuarioPorEmail(@PathVariable String email) {
        return usuarioService.buscarUsuarioPorEmail(email);
    }
    @GetMapping("/")
    public List<UsuarioResponseDTO> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }
    @PutMapping("/{id}")
    public UsuarioResponseDTO atualizarUsuario(@PathVariable Long id, @RequestBody UsuarioRequestDTO data) {
        return usuarioService.atualizarUsuarioPorId(id, data);
    }
    @DeleteMapping("/{id}")
    public void deletarUsuario(@PathVariable Long id) {
        usuarioService.deletarUsuario(id);
    }
    
}
