package com.fernando.estoque_api.mapper;

import com.fernando.estoque_api.dto.usuario.UsuarioResponseDTO;
import com.fernando.estoque_api.entity.Usuario;;

public class UsuarioMapper {
    public UsuarioResponseDTO toDTO(Usuario usuario){
    UsuarioResponseDTO response = new UsuarioResponseDTO();
    response.setName(usuario.getName());
    response.setRole(usuario.getRole());

    return response;
    }

}
