package com.fernando.estoque_api.dto.user;

import java.time.LocalDateTime;

import com.fernando.estoque_api.enums.Role;

import lombok.Getter;
import lombok.Setter;


public class UserResponseDTO {
    
    @Getter
    @Setter
    private Long id;

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

    @Getter
    @Setter
    private LocalDateTime createdAt;
    
    @Getter
    @Setter
    private LocalDateTime updatedAt;

}
