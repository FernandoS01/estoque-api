package com.fernando.estoque_api.dto.user;

import com.fernando.estoque_api.enums.Role;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserRequestDTO {

    private String name;

    private String email;

    private String password;

    private Role role;

}
