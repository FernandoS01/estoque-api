package com.fernando.estoque_api.dto.usuario;

import com.fernando.estoque_api.enums.Role;

import lombok.Getter;
import lombok.Setter;


public class UsuarioRequestDTO {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private Role role;

}
