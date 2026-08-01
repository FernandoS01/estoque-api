package com.fernando.estoque_api.mapper;

import com.fernando.estoque_api.dto.cliente.ClienteResponseDTO;
import com.fernando.estoque_api.entity.Cliente;

public class ClienteMapper {
    public ClienteResponseDTO toDTO(Cliente cliente){
        
        ClienteResponseDTO response = new ClienteResponseDTO();
        
        response.setId(cliente.getId());
        response.setName(cliente.getName());
        response.setEmail(cliente.getEmail());
        response.setCpf(cliente.getCpf());
        response.setPhone(cliente.getPhone());
        response.setCreatedAt(cliente.getCreatedAt());

        return response;
    }
}
