package com.fernando.estoque_api.mapper;

import org.springframework.stereotype.Component;

import com.fernando.estoque_api.dto.sale.SaleResponseDTO;
import com.fernando.estoque_api.entity.Sale;

@Component
public class SaleMapper {
    private final SaleItemMapper saleItemMapper;
    private final UserMapper userMapper;
    private final ClientMapper clientMapper;
    public SaleMapper(UserMapper userMapper, ClientMapper clientMapper, SaleItemMapper saleItemMapper){
        this.userMapper = userMapper;
        this.clientMapper = clientMapper;
        this.saleItemMapper = saleItemMapper;
    }
    public SaleResponseDTO toDTO(Sale sale){
        SaleResponseDTO response = new SaleResponseDTO();
        response.setId(sale.getId());
        response.setUser(userMapper.toDTO(sale.getUser()));
        response.setClient(clientMapper.toDTO(sale.getClient()));
        response.setItens(saleItemMapper.toDTOList(sale.getItens()));
        response.setTotalAmount(sale.getTotalAmount());
        response.setStatus(sale.getStatus());
        response.setSoldAt(sale.getSoldAt());
        return response;

    }
}