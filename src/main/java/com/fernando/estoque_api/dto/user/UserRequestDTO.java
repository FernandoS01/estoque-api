package com.fernando.estoque_api.dto.user;

import com.fernando.estoque_api.enums.Role;

import lombok.Getter;
import lombok.Setter;


public class UserRequestDTO {

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
