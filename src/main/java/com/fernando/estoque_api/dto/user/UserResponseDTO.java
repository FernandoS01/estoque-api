package com.fernando.estoque_api.dto.user;

import java.time.LocalDateTime;

import com.fernando.estoque_api.enums.Role;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserResponseDTO {
    
    private Long id;

    private String name;

    private String email;

    private String password;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
