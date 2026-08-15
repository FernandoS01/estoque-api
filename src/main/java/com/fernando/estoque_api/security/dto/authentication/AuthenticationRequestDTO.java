package com.fernando.estoque_api.security.dto.authentication;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationRequestDTO {
    String email;
    String password;
}
