package com.fernando.estoque_api.mapper;

import org.springframework.stereotype.Component;

import com.fernando.estoque_api.dto.venda.VendaResponseDTO;
import com.fernando.estoque_api.entity.Venda;

@Component
public class VendaMapper {
    private final ItemVendaMapper itemVendaMapper;
    private final UsuarioMapper usuarioMapper;
    private final ClienteMapper clienteMapper;
    public VendaMapper(UsuarioMapper usuarioMapper, ClienteMapper clienteMapper, ItemVendaMapper itemVendaMapper){
        this.usuarioMapper = usuarioMapper;
        this.clienteMapper = clienteMapper;
        this.itemVendaMapper = itemVendaMapper;
    }
    public VendaResponseDTO toDTO(Venda venda){
        VendaResponseDTO response = new VendaResponseDTO();
        response.setId(venda.getId());
        response.setUsuario(usuarioMapper.toDTO(venda.getUsuario()));
        response.setCliente(clienteMapper.toDTO(venda.getCliente()));
        response.setItens(itemVendaMapper.toDTOList(venda.getItens()));
        response.setTotalAmount(venda.getTotalAmount());
        response.setStatus(venda.getStatus());
        response.setSoldAt(venda.getSoldAt());
        return response;

    }
}