package com.fernando.estoque_api.mapper;

import org.springframework.stereotype.Component;

import com.fernando.estoque_api.dto.venda.ItemVendaResponseDTO;
import com.fernando.estoque_api.entity.ItemVenda;


import java.util.List;

@Component
public class ItemVendaMapper {
    private final ProdutoMapper produtoMapper;
    public ItemVendaMapper(ProdutoMapper produtoMapper){
        this.produtoMapper = produtoMapper;
    }
    public ItemVendaResponseDTO toDTO(ItemVenda item){
        ItemVendaResponseDTO response = new ItemVendaResponseDTO();
            response.setId(item.getId());
            response.setProduto(produtoMapper.toDTO(item.getProduto()));
            response.setQuantity(item.getQuantity());
            response.setUnitPrice(item.getUnitPrice());
            response.setSubtotal(item.getSubtotal());

            return response;
    }
    public List<ItemVendaResponseDTO> toDTOList(List<ItemVenda> itens){
        List<ItemVendaResponseDTO> responseList = itens.stream().map(item->{
            return toDTO(item);
        }).toList();


        return responseList;
    }
}
