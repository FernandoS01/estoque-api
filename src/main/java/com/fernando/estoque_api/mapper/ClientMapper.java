package com.fernando.estoque_api.mapper;

import com.fernando.estoque_api.dto.client.ClientResponseDTO;
import com.fernando.estoque_api.entity.Client;

public class ClientMapper {
    public ClientResponseDTO toDTO(Client client){
        
        ClientResponseDTO response = new ClientResponseDTO();
        
        response.setId(client.getId());
        response.setName(client.getName());
        response.setEmail(client.getEmail());
        response.setCpf(client.getCpf());
        response.setPhone(client.getPhone());
        response.setCreatedAt(client.getCreatedAt());

        return response;
    }
}
