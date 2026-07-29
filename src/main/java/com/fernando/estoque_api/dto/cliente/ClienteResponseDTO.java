package com.fernando.estoque_api.dto.cliente;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class ClienteResponseDTO {

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
    private String cpf;

    @Getter
    @Setter
    private String phone;

    @Getter
    @Setter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    private LocalDateTime updatedAt;

}
