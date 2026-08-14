package com.fernando.estoque_api.dto.client;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class ClientResponseDTO {

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
