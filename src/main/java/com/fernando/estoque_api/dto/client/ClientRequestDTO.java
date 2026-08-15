package com.fernando.estoque_api.dto.client;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientRequestDTO {

    private String name;

    private String email;

    private String cpf;

    private String phone;

}
