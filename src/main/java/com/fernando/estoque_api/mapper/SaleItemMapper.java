package com.fernando.estoque_api.mapper;

import org.springframework.stereotype.Component;

import com.fernando.estoque_api.dto.sale.SaleItemResponse;
import com.fernando.estoque_api.entity.SaleItems;


import java.util.List;

@Component
public class SaleItemMapper {
    private final ProductMapper productMapper;
    public SaleItemMapper(ProductMapper productMapper){
        this.productMapper = productMapper;
    }
    public SaleItemResponse toDTO(SaleItems item){
        SaleItemResponse response = new SaleItemResponse();
            response.setId(item.getId());
            response.setProduct(productMapper.toDTO(item.getProduct()));
            response.setQuantity(item.getQuantity());
            response.setUnitPrice(item.getUnitPrice());
            response.setSubtotal(item.getSubtotal());

            return response;
    }
    public List<SaleItemResponse> toDTOList(List<SaleItems> itens){
        List<SaleItemResponse> responseList = itens.stream().map(item->{
            return toDTO(item);
        }).toList();


        return responseList;
    }
}
