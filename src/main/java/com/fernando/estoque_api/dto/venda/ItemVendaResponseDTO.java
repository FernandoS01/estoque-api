package com.fernando.estoque_api.dto.venda;

import java.math.BigDecimal;

import com.fernando.estoque_api.dto.produto.ProdutoResponseDTO;
import com.fernando.estoque_api.dto.usuario.UsuarioResponseDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemVendaResponseDTO {

    private Long id;

    private ProdutoResponseDTO produto;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;
}
