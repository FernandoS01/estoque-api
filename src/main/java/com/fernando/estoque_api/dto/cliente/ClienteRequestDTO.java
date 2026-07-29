package com.fernando.estoque_api.dto.cliente;

import lombok.Getter;
import lombok.Setter;

public class ClienteRequestDTO {

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

}
