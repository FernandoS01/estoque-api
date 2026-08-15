package com.fernando.estoque_api.dto.client;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientResponseDTO {

    private Long id;

    private String name;

    private String email;

    private String cpf;

    private String phone;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}
