package com.fernando.estoque_api.dto.client;

import lombok.Getter;
import lombok.Setter;

public class ClientRequestDTO {

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
