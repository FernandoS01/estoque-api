package com.fernando.estoque_api.security.dto.authentication;

import com.fernando.estoque_api.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationResponseDTO {
    String name;

    String token;

    Role role;

}
